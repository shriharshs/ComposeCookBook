package com.guru.composecookbook.ui.advancelists

import androidx.compose.animation.transition
import androidx.compose.foundation.ScrollableColumn
import androidx.compose.foundation.Text
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.HorizontalGradient
import androidx.compose.ui.unit.dp
import androidx.ui.tooling.preview.Preview
import com.guru.composecookbook.ui.Animations.AnimationDefinitions

enum class ShimmerAnimationType {
    FADE, TRANSLATE, FADETRANSLATE
}

@Preview
@Composable
fun ShimmerList() {
    var shimmerAnimationType by remember { mutableStateOf(ShimmerAnimationType.FADE) }
    var colorState by remember { mutableStateOf(AnimationDefinitions.AnimationState.START) }
    var colorFinalState by remember { mutableStateOf(AnimationDefinitions.AnimationState.END) }
    val dpStartState by remember { mutableStateOf(AnimationDefinitions.AnimationState.START) }
    val dpEndState by remember { mutableStateOf(AnimationDefinitions.AnimationState.END) }

    val shimmerTranslateAnim = transition(
        definition = AnimationDefinitions.shimmerTranslateAnimation,
        initState = dpStartState,
        toState = dpEndState
    )

    val shimmerColorAnim = transition(
        definition = AnimationDefinitions.shimmerColorAnimation,
        initState = colorState,
        toState = colorFinalState,
        onStateChangeFinished = {
            when (it) {
                AnimationDefinitions.AnimationState.START -> {
                    colorState = AnimationDefinitions.AnimationState.START
                    colorFinalState = AnimationDefinitions.AnimationState.MID
                }
                AnimationDefinitions.AnimationState.MID -> {
                    colorState = AnimationDefinitions.AnimationState.MID
                    colorFinalState = AnimationDefinitions.AnimationState.END
                }
                AnimationDefinitions.AnimationState.END -> {
                    colorState = AnimationDefinitions.AnimationState.END
                    colorFinalState = AnimationDefinitions.AnimationState.START
                }
            }
        }
    )

    val list = if (shimmerAnimationType != ShimmerAnimationType.TRANSLATE) {
        listOf(
            shimmerColorAnim[AnimationDefinitions.shimmerColorPropKey],
            shimmerColorAnim[AnimationDefinitions.shimmerColorPropKey].copy(alpha = 0.5f)
        )
    } else {
        listOf(Color.LightGray.copy(alpha = 0.6f), Color.LightGray)
    }

    val dpValue = if (shimmerAnimationType != ShimmerAnimationType.FADE) {
        shimmerTranslateAnim[AnimationDefinitions.shimmerDpPropKey]
    } else {
        2000.dp
    }

    ScrollableColumn(modifier = Modifier.fillMaxSize()) {
        Spacer(modifier = Modifier.height(30.dp))
        Row(horizontalArrangement = Arrangement.SpaceEvenly, modifier = Modifier.fillMaxWidth()) {
            Button(
                onClick = { shimmerAnimationType = ShimmerAnimationType.FADE },
                backgroundColor = if (shimmerAnimationType == ShimmerAnimationType.FADE)
                    MaterialTheme.colors.primary else Color.LightGray,
            ) {
                Text(text = "Fading")
            }
            Button(
                onClick = { shimmerAnimationType = ShimmerAnimationType.TRANSLATE },
                backgroundColor = if (shimmerAnimationType == ShimmerAnimationType.TRANSLATE)
                    MaterialTheme.colors.primary else Color.LightGray
            ) {
                Text(text = "Translating")
            }
            Button(
                onClick = { shimmerAnimationType = ShimmerAnimationType.FADETRANSLATE },
                backgroundColor = if (shimmerAnimationType == ShimmerAnimationType.FADETRANSLATE)
                    MaterialTheme.colors.primary else Color.LightGray
            ) {
                Text(text = "Fade+Translate")
            }
        }

        ShimmerItem(list, dpValue.value)
        ShimmerItemBig(list, dpValue.value)
        ShimmerItem(list, dpValue.value)
        ShimmerItem(list, dpValue.value)
    }
}

@Composable
fun ShimmerItem(lists: List<Color>, floatAnim: Float = 0f) {
    Row(modifier = Modifier.padding(16.dp)) {
        Spacer(
            modifier = Modifier.preferredSize(100.dp)
                .background(brush = HorizontalGradient(lists, 0f, floatAnim))
        )
        Column(modifier = Modifier.padding(8.dp)) {
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .preferredHeight(30.dp)
                    .padding(8.dp).background(brush = HorizontalGradient(lists, 0f, floatAnim))
            )
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .preferredHeight(30.dp)
                    .padding(8.dp)
                    .background(brush = HorizontalGradient(lists, 0f, floatAnim))
            )
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .preferredHeight(30.dp)
                    .padding(8.dp)
                    .background(brush = HorizontalGradient(lists, 0f, floatAnim))
            )
        }
    }
}

@Composable
fun ShimmerItemBig(lists: List<Color>, floatAnim: Float = 0f) {
    Column(modifier = Modifier.padding(16.dp)) {
        Spacer(
            modifier = Modifier.fillMaxWidth().preferredSize(200.dp).background(
                brush = HorizontalGradient(
                    lists, 0f, floatAnim
                )
            )
        )
        Spacer(modifier = Modifier.height(8.dp))
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .preferredHeight(30.dp)
                .padding(vertical = 8.dp)
                .background(brush = HorizontalGradient(lists, 0f, floatAnim))
        )
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .preferredHeight(30.dp)
                .padding(vertical = 8.dp)
                .background(brush = HorizontalGradient(lists, 0f, floatAnim))
        )
    }
}