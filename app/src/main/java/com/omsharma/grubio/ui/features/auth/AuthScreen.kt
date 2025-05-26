package com.omsharma.grubio.ui.features.auth

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.omsharma.grubio.R
import com.omsharma.grubio.ui.GroupSocialButtons
import com.omsharma.grubio.ui.navigation.Login
import com.omsharma.grubio.ui.navigation.Signup
import com.omsharma.grubio.ui.theme.Orange
import com.omsharma.grubio.ui.theme.metropolisFontFamily

@Composable
fun AuthScreen(
    navController: NavController
) {

    val imageSize = remember { mutableStateOf(IntSize.Zero) }

    val brush = Brush.verticalGradient(
        colors = listOf(
            Color.Transparent,
            Color.Black,
        ),
        startY = imageSize.value.height.toFloat() / 3
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        Image(
            painter = painterResource(R.drawable.backround),
            contentDescription = null,
            contentScale = ContentScale.FillBounds,
            modifier = Modifier
                .onGloballyPositioned {
                    imageSize.value = it.size
                }
                .alpha(0.6f)
        )
        Box(
            modifier = Modifier
                .matchParentSize()
                .background(brush = brush)
        )

        Button(
            onClick = {

            },
            colors = ButtonDefaults.buttonColors(containerColor = Color.White),
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(horizontal = 8.dp)
                .safeDrawingPadding()
        ) {
            Text(
                text = stringResource(id = R.string.skip),
                color = Orange,
                fontFamily = metropolisFontFamily,
                fontWeight = FontWeight.Bold
            )
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 120.dp)
                .padding(16.dp),
        ) {
            Text(
                text = stringResource(id = R.string.welcome),
                color = Color.Black,
                fontFamily = metropolisFontFamily,
                fontWeight = FontWeight.ExtraBold,
                fontSize = 42.sp,
            )
            Text(
                text = stringResource(id = R.string.app_name),
                color = Orange,
                fontFamily = metropolisFontFamily,
                fontWeight = FontWeight.ExtraBold,
                fontSize = 42.sp,
            )
            Text(
                text = stringResource(id = R.string.app_desc),
                color = Color.DarkGray,
                fontFamily = metropolisFontFamily,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                modifier = Modifier.padding(top = 16.dp)
            )
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            GroupSocialButtons(
                onFacebookClick = {},
                onGoogleClick = {}
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {navController.navigate(Signup)},
                modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color.Gray.copy(alpha = 0.2f)),
                shape = RoundedCornerShape(32.dp),
                border = BorderStroke(1.dp, Color.White)
            ) {
                Text(
                    text = stringResource(id = R.string.sign_with_email),
                    fontFamily = metropolisFontFamily,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 16.sp,
                    color = Color.White
                )
            }


            Text(
                text = stringResource(id = R.string.already_have_account),
                modifier = Modifier
                    .padding(16.dp)
                    .clickable(interactionSource = null, indication = null) {
                        navController.navigate(Login)
                    },
                color = Color.White,
                fontFamily = metropolisFontFamily,
                fontWeight = FontWeight.SemiBold,
                fontSize = 14.sp,
            )
        }
    }
}

@Preview
@Composable
private fun AuthScreenPreview() {
    AuthScreen(rememberNavController())
}