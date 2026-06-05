package com.example.kotlinbasics.ui.compose

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.ui.text.input.ImeAction

private enum class Screen {
    Menu, Task1, Task2, Task3, Task4, Task5, Task6, Task7, Task8, Task9, Task10
}

@Composable
fun KotlinBasicsApp() {
    var screen by remember { mutableStateOf(Screen.Menu) }

    when (screen) {
        Screen.Menu -> MenuScreen { screen = it }
        Screen.Task1 -> Task1Screen { screen = Screen.Menu }
        Screen.Task2 -> Task2Screen { screen = Screen.Menu }
        Screen.Task3 -> Task3Screen { screen = Screen.Menu }
        Screen.Task4 -> Task4Screen { screen = Screen.Menu }
        Screen.Task5 -> Task5Screen { screen = Screen.Menu }
        Screen.Task6 -> Task6Screen { screen = Screen.Menu }
        Screen.Task7 -> Task7Screen { screen = Screen.Menu }
        Screen.Task8 -> Task8Screen { screen = Screen.Menu }
        Screen.Task9 -> Task9Screen { screen = Screen.Menu }
        Screen.Task10 -> Task10Screen { screen = Screen.Menu }
    }
}

@Composable
private fun MenuScreen(onSelect: (Screen) -> Unit) {
    ScreenColumn {
        Text("Основы Kotlin", style = MaterialTheme.typography.headlineMedium)
        Spacer(Modifier.height(16.dp))
        val buttons = listOf(
            "1. Первая и последняя цифра" to Screen.Task1,
            "2. Сумма и среднее чисел" to Screen.Task2,
            "3. Угадай число" to Screen.Task3,
            "4. Простые числа" to Screen.Task4,
            "5. Больше соседних" to Screen.Task5,
            "6. Произведение, min, max" to Screen.Task6,
            "7. Квадратное уравнение" to Screen.Task7,
            "8. Класс с массивом" to Screen.Task8,
            "9. Класс Vector" to Screen.Task9,
            "10. Наследование Vehicle" to Screen.Task10
        )
        buttons.forEach { (title, destination) ->
            Button(onClick = { onSelect(destination) }, modifier = Modifier.fillMaxWidth()) {
                Text(title, fontSize = 16.sp)
            }
        }
    }
}

@Composable
private fun ScreenColumn(content: @Composable ColumnScope.() -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top,
        content = content
    )
}

@Composable
private fun AppButton(text: String, onClick: () -> Unit) {
    Button(onClick = onClick, modifier = Modifier.fillMaxWidth()) { Text(text) }
}

@Composable
private fun BackButton(onBack: () -> Unit) {
    Button(onClick = onBack, modifier = Modifier.fillMaxWidth()) { Text("Назад") }
    Spacer(Modifier.height(16.dp))
}

@Composable
private fun NumberField(label: String, value: String, onChange: (String) -> Unit) {
    OutlinedTextField(
        value = value,
        onValueChange = onChange,
        label = { Text(label) },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        modifier = Modifier.fillMaxWidth(),
        singleLine = true
    )
}

@Composable
private fun Task1Screen(onBack: () -> Unit) {
    var input by remember { mutableStateOf("") }
    val result = sumFirstAndLastDigit(input)
    ScreenColumn {
        BackButton(onBack)
        Text("Задание 1", style = MaterialTheme.typography.headlineSmall)
        Text("Введите целое положительное число. Программа найдет сумму первой и последней цифры.")
        NumberField("Число", input) { input = it }
        Spacer(Modifier.height(12.dp))
        Text("Результат: ${result?.toString() ?: "введите число"}")
    }
}

@Composable
private fun Task2Screen(onBack: () -> Unit) {
    var input by remember { mutableStateOf("") }

    var count by remember { mutableStateOf(0) }
    var sum by remember { mutableStateOf(0.0) }
    var isFinished by remember { mutableStateOf(false) }
    var enteredNumbersText by remember { mutableStateOf("") }

    val average = if (count == 0) 0.0 else sum / count
    val formattedSum = String.format(java.util.Locale.US, "%.3f", sum)
    val formattedAverage = String.format(java.util.Locale.US, "%.3f", average)

    fun processInput() {
        val value = input.replace(",", ".").toDoubleOrNull()

        if (value != null && !isFinished) {
            if (value == 0.0) {
                isFinished = true
            } else {
                count += 1
                sum += value

                enteredNumbersText = if (enteredNumbersText.isEmpty()) {
                    value.toString()
                } else {
                    "$enteredNumbersText, $value"
                }
            }
        }

        input = ""
    }

    ScreenColumn {
        BackButton(onBack)

        Text("Задание 2", style = MaterialTheme.typography.headlineSmall)

        Text(
            "Пользователь вводит числа по одному. После каждого числа нужно нажать Enter. " +
                    "Ввод продолжается до тех пор, пока не будет введено число 0."
        )

        OutlinedTextField(
            value = input,
            onValueChange = { input = it },
            label = { Text("Введите число") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            enabled = !isFinished,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Decimal,
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    processInput()
                }
            )
        )

        Spacer(modifier = Modifier.height(12.dp))

        if (!isFinished) {
            Text("Нажмите Enter после ввода каждого числа.")
        } else {
            Text("Ввод завершён, так как было введено число 0.")
        }

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = "Введённые числа: ${
                if (enteredNumbersText.isEmpty()) "пока нет" else enteredNumbersText
            }"
        )

        Text("Количество введённых чисел: $count")
        Text("Общая сумма: $formattedSum")
        Text("Среднее арифметическое: $formattedAverage")

        Spacer(modifier = Modifier.height(16.dp))

        AppButton("Заново") {
            input = ""
            count = 0
            sum = 0.0
            isFinished = false
            enteredNumbersText = ""
        }
    }
}

@Composable
private fun Task3Screen(onBack: () -> Unit) {
    var secret by remember { mutableIntStateOf((0..10).random()) }
    var input by remember { mutableStateOf("") }
    var message by remember { mutableStateOf("Введите число от 0 до 10") }
    ScreenColumn {
        BackButton(onBack)
        Text("Задание 3", style = MaterialTheme.typography.headlineSmall)
        Text("Игра \"Угадай число\".")
        NumberField("Ваш вариант", input) { input = it }
        AppButton("Проверить") {
            val guess = input.toIntOrNull()
            message = when {
                guess == null -> "Введите число"
                guess > secret -> "Много"
                guess < secret -> "Мало"
                else -> "Угадал"
            }
        }
        AppButton("Загадать новое число") {
            secret = (0..10).random()
            message = "Введите число от 0 до 10"
            input = ""
        }
        Text(message)
    }
}

@Composable
private fun Task4Screen(onBack: () -> Unit) {
    var input by remember { mutableStateOf("") }
    val count = input.toIntOrNull() ?: 0
    val primes = if (count > 0) firstPrimes(count) else emptyList()
    ScreenColumn {
        BackButton(onBack)
        Text("Задание 4", style = MaterialTheme.typography.headlineSmall)
        Text("Введите количество простых чисел.")
        NumberField("n", input) { input = it }
        Spacer(Modifier.height(12.dp))
        Text(primes.mapIndexed { index, value -> "${index + 1}-ое число: $value" }.joinToString("\n"))
    }
}

@Composable
private fun Task5Screen(onBack: () -> Unit) {
    var input by remember { mutableStateOf("1 5 2 8 3 4") }
    val array = parseIntArray(input)
    ScreenColumn {
        BackButton(onBack)
        Text("Задание 5", style = MaterialTheme.typography.headlineSmall)
        Text("Введите массив чисел через пробел.")
        OutlinedTextField(value = input, onValueChange = { input = it }, label = { Text("Массив") }, modifier = Modifier.fillMaxWidth())
        Text("for: ${biggerThanNeighboursFor(array)}")
        Text("while: ${biggerThanNeighboursWhile(array)}")
        Text("forEach: ${biggerThanNeighboursForEach(array)}")
    }
}

@Composable
private fun Task6Screen(onBack: () -> Unit) {
    var input by remember { mutableStateOf("2 3 4 5") }
    val array = parseIntArray(input)
    ScreenColumn {
        BackButton(onBack)
        Text("Задание 6", style = MaterialTheme.typography.headlineSmall)
        Text("Введите массив чисел через пробел.")
        OutlinedTextField(value = input, onValueChange = { input = it }, label = { Text("Массив") }, modifier = Modifier.fillMaxWidth())
        Text("Произведение for: ${productFor(array)}")
        Text("Произведение while: ${productWhile(array)}")
        Text("Произведение forEach: ${productForEach(array)}")
        Text("Произведение reduce: ${productReduce(array)}")
        Text("min: ${array.minOrNull() ?: 0}, max: ${array.maxOrNull() ?: 0}")
    }
}

@Composable
private fun Task7Screen(onBack: () -> Unit) {
    var a by remember { mutableStateOf("1") }
    var b by remember { mutableStateOf("-3") }
    var c by remember { mutableStateOf("2") }
    val result = quadraticRoot(a.toDoubleOrNull() ?: 0.0, b.toDoubleOrNull() ?: 0.0, c.toDoubleOrNull() ?: 0.0)
    ScreenColumn {
        BackButton(onBack)
        Text("Задание 7", style = MaterialTheme.typography.headlineSmall)
        Text("Решение квадратного уравнения ax² + bx + c = 0.")
        NumberField("a", a) { a = it }
        NumberField("b", b) { b = it }
        NumberField("c", c) { c = it }
        Text(result)
    }
}

@Composable
private fun Task8Screen(onBack: () -> Unit) {
    var input by remember { mutableStateOf("-2 4 5 -1 3") }
    val array = parseIntArray(input)
    val numberArray = NumberArray(array)
    ScreenColumn {
        BackButton(onBack)
        Text("Задание 8", style = MaterialTheme.typography.headlineSmall)
        Text("Класс NumberArray хранит массив и выполняет вычисления.")
        OutlinedTextField(value = input, onValueChange = { input = it }, label = { Text("Массив") }, modifier = Modifier.fillMaxWidth())
        Text("Сумма положительных: ${numberArray.positiveSum()}")
        Text("Произведение: ${numberArray.product()}")
        Text("Среднее: ${numberArray.average()}")
    }
}

@Composable
private fun Task9Screen(onBack: () -> Unit) {
    val first = Vector(1.0, 2.0, 3.0)
    val second = Vector(3.0, 2.0, 1.0)
    ScreenColumn {
        BackButton(onBack)
        Text("Задание 9", style = MaterialTheme.typography.headlineSmall)
        Text("Класс Vector описывает вектор в трехмерном пространстве.")
        Text("Длина первого вектора: ${first.length()}")
        Text("Скалярное произведение методом: ${first.dot(second)}")
        Text("Инфиксная запись: ${first dotInfix second}")
        Text("Оператор *: ${first * second}")
        Text("Внешняя функция: ${dotProduct(first, second)}")
    }
}

@Composable
private fun Task10Screen(onBack: () -> Unit) {
    val vehicles = listOf(Boat(), Plane(), Tank())
    ScreenColumn {
        BackButton(onBack)
        Text("Задание 10", style = MaterialTheme.typography.headlineSmall)
        Text("Наследники класса Vehicle переопределяют название и скорость.")
        vehicles.forEach { vehicle ->
            Text(vehicle.start())
            Text(vehicle.stop())
            Spacer(Modifier.height(8.dp))
        }
    }
}
