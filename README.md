# MyNavDrawer
Project MyNavDrawer merupakan project yang memuat mengenai pemakaian navigation drawer dengan menggunakan compose. Terdapat banyak perbedaan mengenai pembuatan compose dan xml. Compose merupakan bahasa yang deklaratif sedangkan XML adalah bahasa imperatif. Pada project ini menerapkan kegunaaan side effect secara manual untuk tombol back. Selain manual, compose juga menyediakan wrapper bawaan untuk menangani aksi back, yakni BackHandler{ }

## Implementation
1. Membuat Scaffold dan Top App Bar.
2. Membuat Snackbar.
3. Membuat Navigation Drawer.
4. Menangani back press.

## What I Learn
1. Opsi untuk menampilkan navigation drawer ada banyak, contohnya :
  * ModalNavigationDrawer : Pop Up menu yang biasa muncul didepan halaman (yang digunakan di project ini)
  * PermanentNavigationDrawer : Menu sidebar yang selalu muncul di samping halaman utama
  * DismissibleNavigationDrawer : Menu sidebar yang bisa dimunculkan/dihilangkan di samping halaman utama
2. rememberDrawerState : state untuk mengetahui kondisi navigation drawer melalui method isOpen/isClosed
3. remmeberCoroutineScope : memanggil Coroutine di dalam Composable
4. Nilai default state gesture di navigationBar yaitu isOpen, isClosed sehingga diperlukan construktor gestureEnabled = drawerState.isOpen . drawerState merupakan object( val drawerState)
5. LocalContext.current untuk mendapatkan nilai context pada posisi terkini. Contoh case:
  * Untuk menampilkan Toast diperlukan Context yang diperoleh dari LocalContext.current
6. SnackbarResult.ActionPerformed = melakukan aksi
7. SnackbarResult.Dismissed = melakukan sesuatu ketika Snackbar diabaikan
8. Menangani aksi back ada 2 cara, manual dan menggunakan wrapper bawaan yaitu BackHandler{ }
   * Untuk Manual menerapkan beberapa macam Effect API :
      * remmeberUpdatedState = menyimpan status onBack secara aman meskipun nantinya ada perubahan pada parameter lainnya
      * SideEffect = memperbarui callback setiap adanya composition/rekomposisi berhasil dg nilainya. Project ini value ketika berhasil diberi nama enabled
      * DisposableEffect = menghapus backCallback saat meninggalkan composition     
