package com.example.mynavdrawer

import android.content.Context
import android.widget.Toast
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class MyNavDrawerState(
    val drawerState: DrawerState,
    private val scope: CoroutineScope,
    val snackbarHostState: SnackbarHostState,
    private val context: Context,
) {
    fun onMenuClick() {
        scope.launch {
            if (drawerState.isClosed) {
                drawerState.open()
            } else {
                drawerState.close()
            }
        }
    }

    fun onSelectedItem(item: MenuItem) {
        scope.launch {
            drawerState.close()
            val snackbarResult = snackbarHostState.showSnackbar(
                message = context.resources.getString(
                    R.string.coming_soon, item.title
                ),
                actionLabel = context.resources.getString(R.string.subscribe_question),
                withDismissAction = true,
                duration = SnackbarDuration.Short
            )
            if (snackbarResult == SnackbarResult.ActionPerformed) {
                Toast.makeText(
                    context,
                    context.resources.getString(R.string.subscribed_info),
                    Toast.LENGTH_SHORT
                ).show()
            } else if (snackbarResult == SnackbarResult.Dismissed) {
                Toast.makeText(
                    context,
                    "You have Not Subscribe",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    fun onBackPress() {
        if (drawerState.isOpen) {
            scope.launch {
                drawerState.close()
            }
        }
    }
}

@Composable
fun rememberMyNavDrawerState(
    //RemmeberDrawerState untuk mengetahui kondisi navigation drawer dg job isOpen/isClosed
    //rememberCoroutineScope = digunakan untuk memanggil coroutine di dlm composabble
    //why this happen? Fungsi open dn close = susopend Function, krna itu prlu coroutine untuk memanggilnya
    drawerState: DrawerState = rememberDrawerState(initialValue = DrawerValue.Closed),
    coroutineScope: CoroutineScope = rememberCoroutineScope(),

    //Kegunaan LocalContext.current?
    snackbarHostState: SnackbarHostState = remember {
        SnackbarHostState()
    },
    context: Context = LocalContext.current
): MyNavDrawerState =
    remember (drawerState, coroutineScope, snackbarHostState, context) {
        MyNavDrawerState(drawerState, coroutineScope, snackbarHostState,context)
    }