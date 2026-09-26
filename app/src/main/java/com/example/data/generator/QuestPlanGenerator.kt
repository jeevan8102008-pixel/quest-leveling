package com.example.data.generator

import com.example.data.entity.DailyQuestEntity
import com.example.data.entity.PlayerEntity

object QuestPlanGenerator {

  fun generateWeeklyQuests(player: PlayerEntity): List<DailyQuestEntity> {
    val isGym = player.trainingLocation.equals("Gym", ignoreCase = true)
    val pushUpTarget = when (player.fitnessGoal) {
      "Become stronger" -> 25
      "Build muscle" -> 20
      "Get lean" -> 15
      "Aesthetic strength" -> 20
      else -> 15
    }

    val quests = mutableListOf<DailyQuestEntity>()

    for (day in 1..7) {
      // 1. Warm-up (Every Day)
      quests.add(
        DailyQuestEntity(
          userId = player.id,
          dayOfWeek = day,
          title = "Hunter Dynamic Warm-up",
          targetValue = 5,
          unit = "mins",
          category = "WARMUP",
          exerciseKey = "warmup_mobility",
          estimatedMinutes = 5,
          targetMuscles = "Full Body Joint Mobility & Core Activation"
        )
      )

      // 2. Push-ups Daily Quest (Every Day, with adjustable target)
      val dailyPushUpReps = if (day == 7) (pushUpTarget / 2).coerceAtLeast(10) else pushUpTarget
      quests.add(
        DailyQuestEntity(
          userId = player.id,
          dayOfWeek = day,
          title = "Daily Push-ups",
          targetValue = dailyPushUpReps,
          unit = "reps",
          category = "DAILY_CORE",
          exerciseKey = "push_up",
          estimatedMinutes = 10,
          targetMuscles = "Pectorals, Anterior Deltoids, Triceps"
        )
      )

      // 3. Day Specific Workouts (Alternating muscle groups)
      when (day) {
        1 -> {
          // Upper Body: Chest & Arms
          if (isGym) {
            quests.add(
              DailyQuestEntity(
                userId = player.id,
                dayOfWeek = 1,
                title = "Barbell Bench Press",
                targetValue = 10,
                unit = "reps x 4 sets",
                category = "MAIN",
                exerciseKey = "barbell_bench_press",
                estimatedMinutes = 15,
                targetMuscles = "Pectoralis Major, Triceps"
              )
            )
            quests.add(
              DailyQuestEntity(
                userId = player.id,
                dayOfWeek = 1,
                title = "Incline Dumbbell Press",
                targetValue = 12,
                unit = "reps x 3 sets",
                category = "MAIN",
                exerciseKey = "incline_dumbbell_press",
                estimatedMinutes = 12,
                targetMuscles = "Upper Chest, Deltoids"
              )
            )
            quests.add(
              DailyQuestEntity(
                userId = player.id,
                dayOfWeek = 1,
                title = "Cable Tricep Pushdown",
                targetValue = 15,
                unit = "reps x 3 sets",
                category = "MAIN",
                exerciseKey = "tricep_rope_pushdown",
                estimatedMinutes = 10,
                targetMuscles = "Triceps Lateral Head"
              )
            )
          } else {
            quests.add(
              DailyQuestEntity(
                userId = player.id,
                dayOfWeek = 1,
                title = "Chair / Bench Dips",
                targetValue = 15,
                unit = "reps x 3 sets",
                category = "MAIN",
                exerciseKey = "chair_dips",
                estimatedMinutes = 12,
                targetMuscles = "Triceps, Shoulders, Chest"
              )
            )
            quests.add(
              DailyQuestEntity(
                userId = player.id,
                dayOfWeek = 1,
                title = "Hunter Core Plank",
                targetValue = 60,
                unit = "sec x 3 sets",
                category = "MAIN",
                exerciseKey = "plank_core",
                estimatedMinutes = 8,
                targetMuscles = "Transverse Abdominis, Core"
              )
            )
          }
        }
        2 -> {
          // Lower Body: Legs & Core
          if (isGym) {
            quests.add(
              DailyQuestEntity(
                userId = player.id,
                dayOfWeek = 2,
                title = "Barbell Back Squat",
                targetValue = 8,
                unit = "reps x 4 sets",
                category = "MAIN",
                exerciseKey = "barbell_squat",
                estimatedMinutes = 18,
                targetMuscles = "Quadriceps, Glutes, Hamstrings"
              )
            )
            quests.add(
              DailyQuestEntity(
                userId = player.id,
                dayOfWeek = 2,
                title = "45-Degree Leg Press",
                targetValue = 12,
                unit = "reps x 3 sets",
                category = "MAIN",
                exerciseKey = "leg_press",
                estimatedMinutes = 14,
                targetMuscles = "Quadriceps, Gluteus"
              )
            )
          } else {
            quests.add(
              DailyQuestEntity(
                userId = player.id,
                dayOfWeek = 2,
                title = "Deep Bodyweight Squats",
                targetValue = 25,
                unit = "reps x 4 sets",
                category = "MAIN",
                exerciseKey = "bodyweight_squat",
                estimatedMinutes = 15,
                targetMuscles = "Quads, Glutes, Core"
              )
            )
            quests.add(
              DailyQuestEntity(
                userId = player.id,
                dayOfWeek = 2,
                title = "Walking Lunges",
                targetValue = 20,
                unit = "reps x 3 sets",
                category = "MAIN",
                exerciseKey = "walking_lunges",
                estimatedMinutes = 12,
                targetMuscles = "Quads, Calves, Glutes"
              )
            )
          }
        }
        3 -> {
          // Back & Biceps
          if (isGym) {
            quests.add(
              DailyQuestEntity(
                userId = player.id,
                dayOfWeek = 3,
                title = "Lat Pulldown",
                targetValue = 12,
                unit = "reps x 4 sets",
                category = "MAIN",
                exerciseKey = "lat_pulldown",
                estimatedMinutes = 15,
                targetMuscles = "Latissimus Dorsi, Biceps"
              )
            )
            quests.add(
              DailyQuestEntity(
                userId = player.id,
                dayOfWeek = 3,
                title = "Seated Cable Row",
                targetValue = 12,
                unit = "reps x 3 sets",
                category = "MAIN",
                exerciseKey = "seated_cable_row",
                estimatedMinutes = 12,
                targetMuscles = "Middle Traps, Rhomboids"
              )
            )
            quests.add(
              DailyQuestEntity(
                userId = player.id,
                dayOfWeek = 3,
                title = "Dumbbell Bicep Curls",
                targetValue = 12,
                unit = "reps x 3 sets",
                category = "MAIN",
                exerciseKey = "bicep_curl_db",
                estimatedMinutes = 10,
                targetMuscles = "Biceps Brachii"
              )
            )
          } else {
            quests.add(
              DailyQuestEntity(
                userId = player.id,
                dayOfWeek = 3,
                title = "Doorframe / Table Inverted Rows",
                targetValue = 12,
                unit = "reps x 4 sets",
                category = "MAIN",
                exerciseKey = "doorframe_rows",
                estimatedMinutes = 14,
                targetMuscles = "Lats, Rhomboids, Biceps"
              )
            )
            quests.add(
              DailyQuestEntity(
                userId = player.id,
                dayOfWeek = 3,
                title = "Agility Mountain Climbers",
                targetValue = 30,
                unit = "reps x 3 sets",
                category = "MAIN",
                exerciseKey = "mountain_climbers",
                estimatedMinutes = 8,
                targetMuscles = "Core, Cardio, Shoulders"
              )
            )
          }
        }
        4 -> {
          // Active Recovery & Mobility Day
          quests.add(
            DailyQuestEntity(
              userId = player.id,
              dayOfWeek = 4,
              title = "Active Recovery Walk or Jog",
              targetValue = 15,
              unit = "mins",
              category = "MAIN",
              exerciseKey = "warmup_mobility",
              estimatedMinutes = 15,
              targetMuscles = "Cardiovascular & Joint Regeneration"
            )
          )
          quests.add(
            DailyQuestEntity(
              userId = player.id,
              dayOfWeek = 4,
              title = "Hunter Core Plank",
              targetValue = 45,
              unit = "sec x 3 sets",
              category = "MAIN",
              exerciseKey = "plank_core",
              estimatedMinutes = 8,
              targetMuscles = "Core Stability"
            )
          )
        }
        5 -> {
          // Shoulders & Arms
          if (isGym) {
            quests.add(
              DailyQuestEntity(
                userId = player.id,
                dayOfWeek = 5,
                title = "Overhead Dumbbell Press",
                targetValue = 10,
                unit = "reps x 4 sets",
                category = "MAIN",
                exerciseKey = "overhead_shoulder_press",
                estimatedMinutes = 15,
                targetMuscles = "Anterior & Lateral Deltoids"
              )
            )
            quests.add(
              DailyQuestEntity(
                userId = player.id,
                dayOfWeek = 5,
                title = "Dumbbell Bicep Curls",
                targetValue = 12,
                unit = "reps x 3 sets",
                category = "MAIN",
                exerciseKey = "bicep_curl_db",
                estimatedMinutes = 10,
                targetMuscles = "Biceps"
              )
            )
          } else {
            quests.add(
              DailyQuestEntity(
                userId = player.id,
                dayOfWeek = 5,
                title = "Pike Push-ups",
                targetValue = 10,
                unit = "reps x 3 sets",
                category = "MAIN",
                exerciseKey = "pike_pushups",
                estimatedMinutes = 12,
                targetMuscles = "Deltoids, Upper Chest"
              )
            )
            quests.add(
              DailyQuestEntity(
                userId = player.id,
                dayOfWeek = 5,
                title = "Chair / Bench Dips",
                targetValue = 15,
                unit = "reps x 3 sets",
                category = "MAIN",
                exerciseKey = "chair_dips",
                estimatedMinutes = 10,
                targetMuscles = "Triceps, Shoulders"
              )
            )
          }
        }
        6 -> {
          // Full Body Conditioning
          quests.add(
            DailyQuestEntity(
              userId = player.id,
              dayOfWeek = 6,
              title = "Deep Bodyweight Squats",
              targetValue = 20,
              unit = "reps x 3 sets",
              category = "MAIN",
              exerciseKey = "bodyweight_squat",
              estimatedMinutes = 12,
              targetMuscles = "Quads, Glutes"
            )
          )
          quests.add(
            DailyQuestEntity(
              userId = player.id,
              dayOfWeek = 6,
              title = "Agility Mountain Climbers",
              targetValue = 30,
              unit = "reps x 3 sets",
              category = "MAIN",
              exerciseKey = "mountain_climbers",
              estimatedMinutes = 8,
              targetMuscles = "Core, Conditioning"
            )
          )
        }
        7 -> {
          // Rest & Regeneration Day
          quests.add(
            DailyQuestEntity(
              userId = player.id,
              dayOfWeek = 7,
              title = "Deep Regeneration & Mobility",
              targetValue = 15,
              unit = "mins",
              category = "MAIN",
              exerciseKey = "stretching_cooldown",
              estimatedMinutes = 15,
              targetMuscles = "Full Body Connective Tissue"
            )
          )
        }
      }

      // 4. Daily Hydration Quest (Every Day - links to Water Tracker)
      quests.add(
        DailyQuestEntity(
          userId = player.id,
          dayOfWeek = day,
          title = "Hydration Quest: Log Water Target",
          targetValue = player.waterTargetMl,
          unit = "ml",
          category = "WATER",
          exerciseKey = "water_tracker",
          estimatedMinutes = 2,
          targetMuscles = "Cellular Hydration & Energy"
        )
      )

      // 5. Cooldown & Stretching (Every Day)
      quests.add(
        DailyQuestEntity(
          userId = player.id,
          dayOfWeek = day,
          title = "Full Body Cooldown Stretch",
          targetValue = 5,
          unit = "mins",
          category = "COOLDOWN",
          exerciseKey = "stretching_cooldown",
          estimatedMinutes = 5,
          targetMuscles = "Hamstrings, Hips, Lats"
        )
      )
    }

    return quests
  }
}
