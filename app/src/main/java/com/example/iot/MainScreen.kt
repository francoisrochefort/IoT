package com.example.iot

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.iot.ui.theme.IoTTheme

@Composable
fun MainScreen(viewModel: MainViewModel = hiltViewModel()) {
    Text(text = viewModel.debugString)
}

@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    IoTTheme {
        MainScreen()
    }
}