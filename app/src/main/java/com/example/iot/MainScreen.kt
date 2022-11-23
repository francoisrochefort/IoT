package com.example.iot

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Button
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.iot.ui.theme.IoTTheme

@Composable
fun MainScreen(viewModel: MainViewModel = hiltViewModel()) {
    Column {
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = viewModel.command,
            onValueChange = viewModel::onCommandChange,
            singleLine = true
        )
        Button(
            onClick = viewModel::onSend
        ) {
            Text(text = "Send")
        }
        Text(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            text = viewModel.debugString
        )
    }

}

@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    IoTTheme {
        MainScreen()
    }
}