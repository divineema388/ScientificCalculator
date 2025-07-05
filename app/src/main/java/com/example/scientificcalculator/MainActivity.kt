package com.example.scientificcalculator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.scientificcalculator.ui.theme.ScientificCalculatorTheme
import kotlin.math.*

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ScientificCalculatorTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    CalculatorApp()
                }
            }
        }
    }
}

@Composable
fun CalculatorApp() {
    var display by remember { mutableStateOf("0") }
    var operand1 by remember { mutableStateOf<Double?>(null) }
    var operator by remember { mutableStateOf<String?>(null) }
    var isNewNumber by remember { mutableStateOf(true) }

    val green500 = Color(0xFF4CAF50) // A nice green color
    val green700 = Color(0xFF388E3C) // Darker green for accents
    val lightGreen = Color(0xFFA5D6A7) // Lighter green for button text

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(green700)
            .padding(16.dp),
        verticalArrangement = Arrangement.Bottom
    ) {
        // Display
        Text(
            text = display,
            fontSize = 60.sp,
            color = Color.White,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            textAlign = androidx.compose.ui.text.style.TextAlign.End
        )

        // Buttons Grid
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            val buttonRows = listOf(
                listOf("sin", "cos", "tan", "ln", "log"),
                listOf("(", ")", "√", "^", "C"),
                listOf("7", "8", "9", "/", "DEL"),
                listOf("4", "5", "6", "*", "-"),
                listOf("1", "2", "3", "+", "%"),
                listOf(".", "0", "π", "e", "=")
            )

            buttonRows.forEach { row ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    row.forEach { buttonText ->
                        CalculatorButton(
                            text = buttonText,
                            onClick = {
                                when (buttonText) {
                                    in "0".."9", "." -> {
                                        if (isNewNumber) {
                                            display = if (buttonText == ".") "0." else buttonText
                                            isNewNumber = false
                                        } else {
                                            if (buttonText == "." && display.contains(".")) {
                                                // Do nothing if "." is already present
                                            } else {
                                                display += buttonText
                                            }
                                        }
                                    }
                                    "C" -> {
                                        display = "0"
                                        operand1 = null
                                        operator = null
                                        isNewNumber = true
                                    }
                                    "DEL" -> {
                                        if (display.length > 1) {
                                            display = display.dropLast(1)
                                        } else {
                                            display = "0"
                                            isNewNumber = true
                                        }
                                    }
                                    in listOf("+", "-", "*", "/", "^", "%") -> {
                                        if (operand1 == null) {
                                            operand1 = display.toDoubleOrNull()
                                        } else if (operator != null) {
                                            // If there's an existing operation, calculate it first
                                            operand1 = calculateResult(operand1, operator, display.toDoubleOrNull())
                                            display = operand1?.toString() ?: "Error"
                                        }
                                        operator = buttonText
                                        isNewNumber = true
                                    }
                                    "=" -> {
                                        if (operand1 != null && operator != null) {
                                            val result = calculateResult(operand1, operator, display.toDoubleOrNull())
                                            display = result?.toString() ?: "Error"
                                            operand1 = null
                                            operator = null
                                            isNewNumber = true
                                        }
                                    }
                                    "√" -> {
                                        val num = display.toDoubleOrNull()
                                        if (num != null && num >= 0) {
                                            display = sqrt(num).toString()
                                        } else {
                                            display = "Error"
                                        }
                                        isNewNumber = true
                                    }
                                    "sin" -> {
                                        val num = display.toDoubleOrNull()
                                        if (num != null) {
                                            display = sin(Math.toRadians(num)).toString()
                                        } else {
                                            display = "Error"
                                        }
                                        isNewNumber = true
                                    }
                                    "cos" -> {
                                        val num = display.toDoubleOrNull()
                                        if (num != null) {
                                            display = cos(Math.toRadians(num)).toString()
                                        } else {
                                            display = "Error"
                                        }
                                        isNewNumber = true
                                    }
                                    "tan" -> {
                                        val num = display.toDoubleOrNull()
                                        if (num != null) {
                                            display = tan(Math.toRadians(num)).toString()
                                        } else {
                                            display = "Error"
                                        }
                                        isNewNumber = true
                                    }
                                    "ln" -> {
                                        val num = display.toDoubleOrNull()
                                        if (num != null && num > 0) {
                                            display = ln(num).toString()
                                        } else {
                                            display = "Error"
                                        }
                                        isNewNumber = true
                                    }
                                    "log" -> {
                                        val num = display.toDoubleOrNull()
                                        if (num != null && num > 0) {
                                            display = log10(num).toString()
                                        } else {
                                            display = "Error"
                                        }
                                        isNewNumber = true
                                    }
                                    "π" -> {
                                        display = PI.toString()
                                        isNewNumber = true
                                    }
                                    "e" -> {
                                        display = E.toString()
                                        isNewNumber = true
                                    }
                                    // Add handling for "(" and ")" for future advanced parsing
                                    "(" -> { /* Placeholder */ }
                                    ")" -> { /* Placeholder */ }
                                }
                            },
                            buttonColor = if (buttonText == "=") green500 else green700,
                            textColor = if (buttonText == "=" || buttonText == "C" || buttonText == "DEL") Color.White else lightGreen
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun RowScope.CalculatorButton(
    text: String,
    onClick: () -> Unit,
    buttonColor: Color,
    textColor: Color
) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .weight(1f)
            .aspectRatio(1f) // Makes the button square
            .clip(CircleShape), // Bubble effect
        colors = ButtonDefaults.buttonColors(containerColor = buttonColor),
        contentPadding = PaddingValues(0.dp) // Remove default padding
    ) {
        Text(
            text = text,
            fontSize = 24.sp,
            color = textColor,
            fontWeight = FontWeight.Medium
        )
    }
}

fun calculateResult(operand1: Double?, operator: String?, operand2: Double?): Double? {
    if (operand1 == null || operator == null || operand2 == null) {
        return null
    }

    return when (operator) {
        "+" -> operand1 + operand2
        "-" -> operand1 - operand2
        "*" -> operand1 * operand2
        "/" -> if (operand2 != 0.0) operand1 / operand2 else Double.NaN // Handle division by zero
        "^" -> operand1.pow(operand2)
        "%" -> operand1 % operand2
        else -> null
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    ScientificCalculatorTheme {
        CalculatorApp()
    }
}