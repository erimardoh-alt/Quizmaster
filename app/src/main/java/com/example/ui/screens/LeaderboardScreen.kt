package com.example.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.Sync
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.LeaderboardUser
import com.example.ui.theme.AmberFlame
import com.example.ui.theme.EmeraldChem
import com.example.ui.theme.NeonCyan
import com.example.ui.theme.ObsidianBg
import com.example.ui.theme.ObsidianCard
import com.example.ui.theme.ObsidianCardBorder
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary
import com.example.ui.theme.VioletIon

@Composable
fun LeaderboardScreen(
  leaderboard: List<LeaderboardUser>,
  modifier: Modifier = Modifier
) {
  var selectedLeague by remember { mutableStateOf("Todas") }

  val leagues = listOf("Todas", "Diamante Químico", "Platino", "Oro", "Plata")

  val filteredUsers = if (selectedLeague == "Todas") {
    leaderboard
  } else {
    leaderboard.filter { it.league.contains(selectedLeague, ignoreCase = true) }
  }

  val top3 = leaderboard.take(3)

  LazyColumn(
    modifier = modifier
      .fillMaxSize()
      .background(ObsidianBg)
      .padding(horizontal = 16.dp),
    contentPadding = PaddingValues(top = 16.dp, bottom = 90.dp),
    verticalArrangement = Arrangement.spacedBy(16.dp)
  ) {
    // 1. Title & Cross-Platform Sync Card
    item {
      Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
        Text(
          text = "Tablas de Clasificación Global",
          fontWeight = FontWeight.Black,
          fontSize = 22.sp,
          color = TextPrimary
        )
        Text(
          text = "Compite contra los mejores estudiantes de química del mundo",
          fontSize = 13.sp,
          color = TextSecondary
        )
      }
    }

    // Cross-platform sync status banner
    item {
      Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        color = Color(0xFF0F172A),
        border = BorderStroke(1.dp, NeonCyan.copy(alpha = 0.3f))
      ) {
        Row(
          modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
          verticalAlignment = Alignment.CenterVertically,
          horizontalArrangement = Arrangement.SpaceBetween
        ) {
          Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
          ) {
            Icon(
              imageVector = Icons.Default.Sync,
              contentDescription = null,
              tint = NeonCyan,
              modifier = Modifier.size(16.dp)
            )
            Column {
              Text(
                text = "Cuenta Sincronizada Multiplataforma",
                fontSize = 11.5.sp,
                fontWeight = FontWeight.Bold,
                color = TextPrimary
              )
              Text(
                text = "Compatible iOS, iPhone & Android • ID: QM-CHEM-9842",
                fontSize = 10.sp,
                color = TextSecondary
              )
            }
          }

          Surface(
            shape = RoundedCornerShape(6.dp),
            color = EmeraldChem.copy(alpha = 0.2f)
          ) {
            Text(
              text = "EN LÍNEA",
              modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
              fontSize = 9.sp,
              fontWeight = FontWeight.Black,
              color = EmeraldChem
            )
          }
        }
      }
    }

    // 2. Podium (Top 3)
    if (top3.size >= 3) {
      item {
        Card(
          modifier = Modifier.fillMaxWidth(),
          shape = RoundedCornerShape(20.dp),
          colors = CardDefaults.cardColors(containerColor = ObsidianCard),
          border = BorderStroke(1.dp, ObsidianCardBorder)
        ) {
          Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(12.dp)
          ) {
            Text(
              text = "Top Alquimistas Estudiantiles",
              fontWeight = FontWeight.Bold,
              fontSize = 14.sp,
              color = TextSecondary
            )

            Row(
              modifier = Modifier.fillMaxWidth(),
              horizontalArrangement = Arrangement.SpaceEvenly,
              verticalAlignment = Alignment.Bottom
            ) {
              // 2nd Place
              PodiumColumn(
                user = top3[1],
                placeNumber = 2,
                placeColor = Color(0xFFC0C0C0),
                podiumHeight = 70.dp
              )

              // 1st Place
              PodiumColumn(
                user = top3[0],
                placeNumber = 1,
                placeColor = AmberFlame,
                podiumHeight = 95.dp
              )

              // 3rd Place
              PodiumColumn(
                user = top3[2],
                placeNumber = 3,
                placeColor = Color(0xFFCD7F32),
                podiumHeight = 55.dp
              )
            }
          }
        }
      }
    }

    // 3. League Filters
    item {
      LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        items(leagues) { league ->
          val isSelected = selectedLeague == league
          Surface(
            modifier = Modifier.clip(RoundedCornerShape(12.dp)),
            color = if (isSelected) NeonCyan.copy(alpha = 0.2f) else ObsidianCard,
            border = BorderStroke(1.dp, if (isSelected) NeonCyan else ObsidianCardBorder)
          ) {
            Text(
              text = league,
              modifier = Modifier
                .padding(horizontal = 12.dp, vertical = 6.dp),
              fontSize = 12.sp,
              fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
              color = if (isSelected) TextPrimary else TextSecondary
            )
          }
        }
      }
    }

    // 4. Ranked List
    items(filteredUsers) { user ->
      val isSelf = user.isCurrentUser
      Card(
        modifier = Modifier
          .fillMaxWidth()
          .testTag("leaderboard_user_${user.rank}"),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(
          containerColor = if (isSelf) NeonCyan.copy(alpha = 0.12f) else ObsidianCard
        ),
        border = BorderStroke(
          if (isSelf) 1.5.dp else 1.dp,
          if (isSelf) NeonCyan else ObsidianCardBorder
        )
      ) {
        Row(
          modifier = Modifier
            .fillMaxWidth()
            .padding(14.dp),
          verticalAlignment = Alignment.CenterVertically
        ) {
          // Rank Badge
          Surface(
            modifier = Modifier.size(34.dp),
            shape = CircleShape,
            color = when (user.rank) {
              1 -> AmberFlame.copy(alpha = 0.2f)
              2 -> Color(0xFFC0C0C0).copy(alpha = 0.2f)
              3 -> Color(0xFFCD7F32).copy(alpha = 0.2f)
              else -> Color(0xFF0F172A)
            }
          ) {
            Box(contentAlignment = Alignment.Center) {
              Text(
                text = "#${user.rank}",
                fontWeight = FontWeight.Black,
                fontSize = 13.sp,
                color = when (user.rank) {
                  1 -> AmberFlame
                  2 -> Color(0xFFE2E8F0)
                  3 -> Color(0xFFF97316)
                  else -> TextSecondary
                }
              )
            }
          }

          Spacer(modifier = Modifier.width(12.dp))

          // Avatar
          Text(text = user.avatarSymbol, fontSize = 22.sp)

          Spacer(modifier = Modifier.width(12.dp))

          // User details
          Column(modifier = Modifier.weight(1f)) {
            Row(
              verticalAlignment = Alignment.CenterVertically,
              horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
              Text(
                text = user.username,
                fontWeight = FontWeight.Bold,
                fontSize = 14.5.sp,
                color = TextPrimary
              )
              if (isSelf) {
                Surface(
                  shape = RoundedCornerShape(4.dp),
                  color = NeonCyan
                ) {
                  Text(
                    text = "TÚ",
                    modifier = Modifier.padding(horizontal = 4.dp, vertical = 1.dp),
                    fontSize = 9.sp,
                    fontWeight = FontWeight.Black,
                    color = Color.Black
                  )
                }
              }
            }

            Text(
              text = "${user.title} • ${user.institution}",
              fontSize = 11.5.sp,
              color = TextSecondary,
              maxLines = 1
            )
          }

          // XP & Winrate
          Column(horizontalAlignment = Alignment.End) {
            Text(
              text = "${user.xp} XP",
              fontWeight = FontWeight.ExtraBold,
              fontSize = 14.sp,
              color = if (isSelf) NeonCyan else VioletIon
            )
            Text(
              text = "${user.winRate} victorias",
              fontSize = 11.sp,
              color = EmeraldChem
            )
          }
        }
      }
    }
  }
}

@Composable
fun PodiumColumn(
  user: LeaderboardUser,
  placeNumber: Int,
  placeColor: Color,
  podiumHeight: androidx.compose.ui.unit.Dp
) {
  Column(
    horizontalAlignment = Alignment.CenterHorizontally,
    verticalArrangement = Arrangement.Bottom
  ) {
    Text(text = user.avatarSymbol, fontSize = 24.sp)
    Spacer(modifier = Modifier.height(2.dp))
    Text(
      text = user.username.split(" ").first(),
      fontSize = 12.sp,
      fontWeight = FontWeight.Bold,
      color = TextPrimary
    )
    Text(
      text = "${user.xp} XP",
      fontSize = 10.5.sp,
      color = placeColor,
      fontWeight = FontWeight.SemiBold
    )
    Spacer(modifier = Modifier.height(6.dp))

    // Step block
    Surface(
      modifier = Modifier
        .width(80.dp)
        .height(podiumHeight),
      shape = RoundedCornerShape(topStart = 10.dp, topEnd = 10.dp),
      color = placeColor.copy(alpha = 0.2f),
      border = BorderStroke(1.dp, placeColor)
    ) {
      Box(contentAlignment = Alignment.Center) {
        Text(
          text = "$placeNumber",
          fontWeight = FontWeight.Black,
          fontSize = 24.sp,
          color = placeColor
        )
      }
    }
  }
}
