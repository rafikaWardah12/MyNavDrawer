package com.example.mynavdrawer

import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.activity.compose.BackHandler
import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.materialIcon
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch

@Composable
fun MyNavDrawerApp() {
    //WITH NO VIEW MODEL
    /*
    //RemmeberDrawerState untuk mengetahui kondisi navigation drawer dg job isOpen/isClosed
    //rememberCoroutineScope = digunakan untuk memanggil coroutine di dlm composabble
    //why this happen? Fungsi open dn close = susopend Function, krna itu prlu coroutine untuk memanggilnya
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }
    //Kegunaan LocalContext.current?
    val context = LocalContext.current
     */

    val appState = rememberMyNavDrawerState()

    //WITH NO VIEW MODEL
    /*
    //Backhandler
    BackPressHandler(enabled = drawerState.isOpen) {
        scope.launch {
            drawerState.close()
        }
    }
    */

    //Bckhandler
    BackPressHandler(enabled = appState.drawerState.isOpen) {
        appState.onBackPress()
    }

    val items = listOf(
        MenuItem(
            title = stringResource(id = R.string.home),
            icon = Icons.Default.Home
        ),
        MenuItem(
            title = stringResource(id = R.string.favourite),
            icon = Icons.Default.Favorite
        ),
        MenuItem(
            title = stringResource(id = R.string.profile),
            icon = Icons.Default.AccountCircle
        ),
    )

    val selectedItem = remember { mutableStateOf(items[0]) }

    Scaffold(
        /*With No View Model
//        snackbarHost = { SnackbarHost(snackbarHostState) },

         */
        snackbarHost = { SnackbarHost(appState.snackbarHostState) },
        topBar = {
            MyTopBar(
                /* With No ViewModel
                onMenuClick = {
                    //logika bisnisnya
                    scope.launch {
                        //isOpen, isCancel dll termasuk job
                        if (drawerState.isClosed) {
                            drawerState.open()
                        } else {
                            drawerState.close()
                        }
                    }
                    */
                onMenuClick = appState::onMenuClick
            )
        },
    ) { paddingValues ->
        ModalNavigationDrawer(
            /* With No View Model
            modifier = Modifier.padding(paddingValues),
            drawerState = drawerState,
            //Default gesture = open dan close. Misal dideklare hnya itu aja yg dipakai
            gesturesEnabled = drawerState.isOpen,
            drawerContent = {
             */
            modifier = Modifier.padding(paddingValues),
            drawerState = appState.drawerState,
            gesturesEnabled = appState.drawerState.isOpen,
            drawerContent = {
                ModalDrawerSheet {
                    Spacer(modifier = Modifier.height(12.dp))
                    items.forEach { item ->
                        NavigationDrawerItem(
                            icon = { Icon(imageVector = item.icon, contentDescription = null) },
                            label = { Text(text = item.title) },
                            selected = item == selectedItem.value,
                            onClick = {
                                /* With No View Model
                                scope.launch {
                                    drawerState.close()
                                    //AKSI PADA SNACKBAR
                                    val snackBarResult = snackbarHostState.showSnackbar(
                                        //Ditambah item.title krna pda string ada %s yg menandakan adanya variabel
                                        message = context.resources.getString(
                                            R.string.coming_soon,
                                            item.title
                                        ),
                                        actionLabel = context.resources.getString(R.string.subscribe_question),
                                        withDismissAction = true,
                                        duration = SnackbarDuration.Short
                                    )
                                    if (snackBarResult == SnackbarResult.ActionPerformed) {
                                        Toast.makeText(
                                            context,
                                            context.resources.getString(R.string.subscribed_info),
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    } else if (snackBarResult == SnackbarResult.Dismissed) {
                                        Toast.makeText(
                                            context,
                                            "Yo have not subrice",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    }
                                }
                                 */
                                
                                appState.onSelectedItem(item)
                                selectedItem.value = item
                            },
                            modifier = Modifier.padding(horizontal = 12.dp)
                        )
                    }
                }
            },
            content = {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center,
                ) {
                    Text(
                        /* With No ViewModel
                        text =
                        if (drawerState.isClosed) {
                            stringResource(id = R.string.swipe_to_open)
                        } else {
                            stringResource(id = R.string.swipe_to_close)
                        }
                         */
                        text = if (appState.drawerState.isClosed){
                            stringResource(id = R.string.swipe_to_open)
                        } else {
                            stringResource(id = R.string.swipe_to_close)
                        }
                    )
                }
            }
        )
    }
}


//WRAPPER Bawaan untuk menangani aksi back
//Disini mencoba memakai side effect secara MANUAL
//    BackHandler {
//
//    }

@Composable
fun BackPressHandler(enabled: Boolean = true, onBackPressed: () -> Unit) {
    //remmeberUpdateState = menyimpan status onBack secara aman meskipun ada perubahan pda parameter
    // lainnya
    val currentOnBackPressed by rememberUpdatedState(newValue = onBackPressed)
    val backCallback = remember {
        object : OnBackPressedCallback(enabled) {
            override fun handleOnBackPressed() {
                currentOnBackPressed()
            }
        }
    }

    //Memperbarui callback stiap kali composition/rekomposisi brhasil dg nilai enabled
    SideEffect {
        backCallback.isEnabled = enabled
    }

    val backDispatcher = checkNotNull(LocalOnBackPressedDispatcherOwner.current) {
        "No OnBackPressedDispatcherOwner was provided via LocalOnBackPressedDispatcherOwner"
    }.onBackPressedDispatcher

    val lifecycleOwner = LocalLifecycleOwner.current
    //DisposableEffect = Menghapus backCallBack saat meninggalkan composition
    DisposableEffect(lifecycleOwner, backDispatcher) {
        backDispatcher.addCallback(lifecycleOwner, backCallback)
        onDispose {
            backCallback.remove()
        }
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyTopBar(onMenuClick: () -> Unit) {
    TopAppBar(
        navigationIcon = {
            IconButton(onClick = {
                onMenuClick()
            }) {
                Icon(
                    imageVector = Icons.Default.Menu,
                    contentDescription = stringResource(R.string.menu)
                )
            }
        },
        title = {
            Text(stringResource(R.string.app_name))
        },
    )
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MyNavDrawerApp()
}

