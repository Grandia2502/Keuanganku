package org.d3if3096.mobpro1.ui.screen

import android.content.Context
import android.content.Intent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import org.d3if3096.mobpro1.R
import org.d3if3096.mobpro1.navigation.Screen
import org.d3if3096.mobpro1.ui.theme.Mobpro1Theme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(navController: NavHostController) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = stringResource(id = R.string.app_name))
                },
                colors = TopAppBarDefaults.mediumTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary
                ),
                actions = {
                    IconButton(onClick = {
                        navController.navigate(Screen.About.route)
                    }) {
                        Icon(
                            imageVector = Icons.Outlined.Info,
                            contentDescription = stringResource(id = R.string.tentang_aplikasi),
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            )
        }
    ) { padding ->
        ScreenContent(Modifier.padding(padding))
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScreenContent(modifier: Modifier) {
    var jumlahUang by rememberSaveable { mutableStateOf("") }
    var jumlahUangError by rememberSaveable { mutableStateOf(false) }

    var keterangan by rememberSaveable { mutableStateOf("") }
    var keteranganError by rememberSaveable { mutableStateOf(false) }

    var jumlahUangX by rememberSaveable { mutableFloatStateOf(0f) }
    var keteranganX by rememberSaveable { mutableStateOf("") }

    val context = LocalContext.current

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(id = R.string.keuanganku_intro),
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = jumlahUang, onValueChange = { jumlahUang = it },
            label = { Text(text = stringResource(id = R.string.jumlah_uang)) },
            trailingIcon = { IconPicker(isError = jumlahUangError) },
//          leadingIcon = { Text(text = "Rp.")},
            isError = jumlahUangError,
            supportingText = { ErrorHint(isError = jumlahUangError) },
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Next
            ),
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = keterangan, onValueChange = { newText -> keterangan = newText },
            label = { Text(text = stringResource(id = R.string.keterangan)) },
            trailingIcon = { IconPicker(isError = jumlahUangError) },
            isError = keteranganError,
            supportingText = { ErrorHint(isError = keteranganError) },
            singleLine = false,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Done,
            ),
            modifier = Modifier.fillMaxWidth()
        )
        Row {
            Button(
                onClick = {
                    jumlahUangError = (jumlahUang == "" || jumlahUang == "0")
                    keteranganError = (keterangan == "" || keterangan == "0")
                    if (jumlahUangError || keteranganError) return@Button

                    jumlahUangX = jumlahUang.toFloat()
                    keteranganX = keterangan
                },
                modifier = Modifier.padding(top = 8.dp, end = 16.dp),
                contentPadding = PaddingValues(horizontal = 32.dp, vertical = 16.dp)
            ) {
                Text(text = stringResource(id = R.string.input_data))
            }
            Button(
                onClick = {
                    jumlahUang = ""
                    keterangan = ""
                    jumlahUangX = 0f
                    keteranganX = ""

                },
                modifier = Modifier.padding(top = 8.dp),
                contentPadding = PaddingValues(horizontal = 32.dp, vertical = 16.dp)
            ) {
                Text(text = stringResource(id = R.string.reset))
            }
        }

        if (jumlahUangX != 0f) {
        Divider(
            modifier = Modifier.padding(vertical = 8.dp),
            thickness = 1.dp
        )
        Text(
            text = stringResource(id = R.string.uang_x, jumlahUangX),
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp, start = 24.dp, end = 24.dp)
        )
        Text(
            text = stringResource(id = R.string.keterangan_x, keteranganX),
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp, start = 24.dp, end = 24.dp)
        )
        Button(
            onClick = {
                shareData(
                    context = context,
                    message = context.getString(
                        R.string.bagikan_template,
                        jumlahUangX, keteranganX
                    )
                )
            },
            modifier = Modifier.padding(top = 8.dp),
            contentPadding = PaddingValues(horizontal = 32.dp, vertical = 16.dp)
        ) {
            Text(text = stringResource(id = R.string.bagikan))
        }
        }
    }
}

@Composable
fun IconPicker(isError: Boolean) {
    if (isError) {
        Icon(imageVector = Icons.Filled.Warning, contentDescription = null)
    }
}

@Composable
fun ErrorHint(isError: Boolean) {
    if (isError) {
        Text(text = stringResource(id = R.string.input_invalid))
    }
}

private fun shareData(context: Context, message: String) {
    val shareIntent = Intent(Intent.ACTION_SEND).apply {
        type = "text/plain"
        putExtra(Intent.EXTRA_TEXT, message)
    }
    if (shareIntent.resolveActivity(context.packageManager) != null) {
        context.startActivity(shareIntent)
    }
}

@Preview(showBackground = true)
//@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
fun ScreenPreview() {
    Mobpro1Theme {
        MainScreen(rememberNavController())
    }
}