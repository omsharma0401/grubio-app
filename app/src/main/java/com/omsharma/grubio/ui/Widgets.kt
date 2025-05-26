package com.omsharma.grubio.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.omsharma.grubio.R
import com.omsharma.grubio.ui.theme.Orange
import com.omsharma.grubio.ui.theme.metropolisFontFamily

@Composable
fun GroupSocialButtons(
    modifier: Modifier = Modifier,
    color: Color = Color.Gray,
    onFacebookClick: () -> Unit,
    onGoogleClick: () -> Unit,
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            HorizontalDivider(
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 8.dp, end = 8.dp),
                thickness = 1.dp,
                color = color
            )

            Text(
                color = color,
                text = stringResource(id = R.string.sign_up_with),
                fontFamily = metropolisFontFamily,
                fontWeight = FontWeight.SemiBold,
                fontSize = 14.sp
            )

            HorizontalDivider(
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 8.dp, end = 8.dp),
                thickness = 1.dp,
                color = color
            )

        }

        Spacer(modifier = Modifier.size(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            SocialButton(
                modifier = modifier.weight(1f),
                icon = R.drawable.ic_facebook,
                title = R.string.sign_up_with_facebook,
                onClick = onFacebookClick,
            )
            SocialButton(
                modifier = modifier.weight(1f),
                icon = R.drawable.ic_google,
                title = R.string.sign_up_with_google,
                onClick = onGoogleClick,
            )
        }

    }
}


@Composable
fun SocialButton(
    modifier: Modifier = Modifier,
    icon: Int,
    title: Int,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(containerColor = Color.White),
        shape = RoundedCornerShape(32.dp),
        modifier = modifier.padding(4.dp)
    ) {
        Row(
            modifier = Modifier.height(36.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = icon),
                contentDescription = null,
                modifier = Modifier.size(24.dp)
            )

            Spacer(modifier = Modifier.size(8.dp))

            Text(
                text = stringResource(id = title),
                fontFamily = metropolisFontFamily,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}


@Composable
fun CustomTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    readOnly: Boolean = false,
    textStyle: TextStyle = LocalTextStyle.current,
    label: @Composable (() -> Unit)? = null,
    placeholder: @Composable (() -> Unit)? = null,
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    prefix: @Composable (() -> Unit)? = null,
    suffix: @Composable (() -> Unit)? = null,
    supportingText: @Composable (() -> Unit)? = null,
    isError: Boolean = false,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    singleLine: Boolean = false,
    maxLines: Int = if (singleLine) 1 else Int.MAX_VALUE,
    minLines: Int = 1,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    shape: Shape = RoundedCornerShape(10.dp),
    colors: TextFieldColors = OutlinedTextFieldDefaults.colors().copy(
        focusedIndicatorColor = Orange,
        unfocusedIndicatorColor = Color.LightGray.copy(alpha = 0.4f),
        focusedTextColor = Color.Black,
        unfocusedTextColor = Color.Black,
    )
) {
    Column(Modifier.padding(vertical = 8.dp)) {
        label?.let {
            Row {
                it()
            }
        }
        Spacer(modifier = Modifier.size(8.dp))
        OutlinedTextField(
            value = value,
            onValueChange,
            modifier,
            enabled,
            readOnly,
            textStyle.copy(fontWeight = FontWeight.SemiBold),
            null,
            placeholder,
            leadingIcon,
            trailingIcon,
            prefix,
            suffix,
            supportingText,
            isError,
            visualTransformation,
            keyboardOptions,
            keyboardActions,
            singleLine,
            maxLines,
            minLines,
            interactionSource,
            shape,
            colors
        )
    }
}