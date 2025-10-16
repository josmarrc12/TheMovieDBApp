package com.osmar.themoviedbapp.ui.menu

import android.app.Activity
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.KeyboardArrowRight
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.outlined.Bookmarks
import androidx.compose.material.icons.outlined.Language
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import com.osmar.themoviedbapp.R
import com.osmar.themoviedbapp.datastore.LanguagePreferences.Languages
import com.osmar.themoviedbapp.ui.common.CommonHeader
import com.osmar.themoviedbapp.utils.Utils.provideColor
import com.osmar.themoviedbapp.utils.updateLocale


@Composable
fun MenuScreen(
    viewModel: MenuViewModel = hiltViewModel(),
    navigationReset:()->Unit,
    navigationBack:()->Unit,
    navigateToBookmarks:()->Unit,
){

    var showDialog by remember { mutableStateOf(false) }
    if (showDialog){
        DialogSelectLanguage(viewModel = viewModel,
            onDismissRequest = { showDialog = false },
            navigationReset = { navigationReset() })
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.background)
    ){
        CommonHeader(title = stringResource(R.string.settings), true){
            navigationBack()
        }
        CategoryItem(
            stringResource(R.string.language_menu), Icons.Outlined.Language
        ){
            showDialog = true
        }
        CategoryItem(
            stringResource(R.string.bookmarks), Icons.Outlined.Bookmarks,
        ){
            navigateToBookmarks()
        }
    }
}

@Composable
fun CategoryItem(
    categoryName : String,
    categoryIcon : ImageVector,
    categoryNav : ()->Unit
){
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp, vertical = 8.dp)
            .clickable {
                categoryNav()
                Log.d("Config", "To Language")
            },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ){
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            Icon(
                modifier = Modifier,
                imageVector = categoryIcon,
                contentDescription = "",
                tint = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.width(24.dp))
            Text(
                modifier = Modifier,
                text = categoryName,
                color = MaterialTheme.colorScheme.secondary
            )
        }

        Icon(
            modifier = Modifier,
            imageVector = Icons.AutoMirrored.Outlined.KeyboardArrowRight,
            contentDescription = "",
            tint = MaterialTheme.colorScheme.primary
        )
    }
}

@Composable
fun DialogSelectLanguage(
    viewModel: MenuViewModel,
    onDismissRequest : ()->Unit,
    navigationReset : ()->Unit,
){
    val languageSelected by viewModel.languageState.collectAsState()
    val activeColor = MaterialTheme.colorScheme.primaryContainer
    val passiveColor = MaterialTheme.colorScheme.onPrimaryContainer
    val context = LocalContext.current
    val resetApp = viewModel.resetFlag.collectAsState()
    if (resetApp.value == 1){
        (context as Activity).recreate()
        navigationReset()
    }
    var languages by remember { mutableStateOf(Languages.UNSPECIFIED) }
    var showDialog by remember { mutableStateOf(false) }
    if (showDialog){
        DialogLanguageReset(languages,context,viewModel) { showDialog = false  }
    }

    Dialog(
        onDismissRequest = {onDismissRequest()}
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(.4f)
                .background(MaterialTheme.colorScheme.secondary),
            verticalArrangement = Arrangement.SpaceBetween
        ){
            Column(
                modifier = Modifier
                    .weight(5f),
                horizontalAlignment = Alignment.CenterHorizontally
            ){
                Text(
                    text = stringResource(R.string.language),
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSecondary,
                    modifier = Modifier.padding(top = 24.dp, bottom = 8.dp)
                )

                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp, vertical = 4.dp),
                    onClick = {
                        if (languageSelected == Languages.ENGLISH){
                            Toast.makeText(context,R.string.language_is_already_in_use, Toast.LENGTH_SHORT).show()
                        }else{
                            languages = Languages.ENGLISH
                            showDialog = true
                        }
                    },
                    colors = ButtonDefaults.buttonColors(
                        contentColor = provideColor(languageSelected == Languages.ENGLISH,passiveColor, activeColor),
                        containerColor = provideColor(languageSelected == Languages.ENGLISH,activeColor, passiveColor),
                    )
                ) {
                    Text(
                        text = "English",
                        style = MaterialTheme.typography.titleSmall
                    )
                }

                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp, vertical = 4.dp),
                    onClick = {
                        if (languageSelected == Languages.SPANISH){
                            Toast.makeText(context, "asd", Toast.LENGTH_SHORT).show()
                        }else{
                            languages = Languages.SPANISH
                            showDialog = true
                        }
                    },
                    colors = ButtonDefaults.buttonColors(
                        contentColor = provideColor(languageSelected == Languages.SPANISH,passiveColor, activeColor),
                        containerColor = provideColor(languageSelected == Languages.SPANISH,activeColor, passiveColor),
                    )
                ) {
                    Text(
                        text = "Español",
                        style = MaterialTheme.typography.titleSmall
                    )
                }
            }

            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                onClick = {onDismissRequest()},
                colors = ButtonDefaults.buttonColors(
                    contentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                ),
                shape = RectangleShape
            ) {
                Text(
                    text = stringResource(R.string.close),
                    style = MaterialTheme.typography.titleSmall
                )
            }
        }
    }
}

@Composable
fun DialogLanguageReset(
    language: Languages,
    context: Context,
    viewModel: MenuViewModel,
    onDismissRequest:() -> Unit
){
    Dialog(
        onDismissRequest = {onDismissRequest()}
    ){
        Box {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(.2f)
                    .background(MaterialTheme.colorScheme.tertiaryContainer),
                horizontalAlignment = Alignment.CenterHorizontally,
            ){
                Text(
                    text = stringResource(R.string.are_you_sure),
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onTertiaryContainer,
                    modifier = Modifier.padding(top = 24.dp, bottom = 8.dp)
                )

                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp, vertical = 4.dp),
                    onClick = {
                        changeLanguage(language, viewModel, context)
                    },
                    colors = ButtonDefaults.buttonColors(
                        contentColor = MaterialTheme.colorScheme.onTertiary,
                        containerColor = MaterialTheme.colorScheme.tertiary,
                    )
                ) {
                    Text(
                        text = stringResource(R.string.accept),
                        style = MaterialTheme.typography.titleSmall
                    )
                }
            }
            IconButton(
                modifier = Modifier
                    .align(Alignment.TopEnd),
                onClick = {
                    onDismissRequest()
                }
            ) {
                Icon(
                    imageVector = Icons.Filled.Close,
                    contentDescription = "",
                    tint = MaterialTheme.colorScheme.onTertiaryContainer
                )
            }

        }
    }
}

private fun changeLanguage(
    language: Languages,
    viewModel: MenuViewModel,
    context: Context){
    viewModel.changeLanguage(language)
    context.updateLocale(language)
}