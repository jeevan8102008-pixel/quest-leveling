package com.example.data.model

import com.example.R

fun getExerciseImageRes(exerciseId: String): Int {
  return when (exerciseId) {
    "push_up", "knee_push_up", "chair_dips" -> R.drawable.workout_push_up
    "barbell_bench_press", "incline_dumbbell_press" -> R.drawable.workout_bench_press
    "lat_pulldown", "seated_cable_row", "doorframe_rows" -> R.drawable.workout_lat_pulldown
    "barbell_squat", "leg_press", "bodyweight_squat", "walking_lunges" -> R.drawable.workout_squat
    "overhead_shoulder_press", "warmup_mobility", "pike_pushups" -> R.drawable.workout_shoulder_press
    "bicep_curl_db", "tricep_rope_pushdown" -> R.drawable.workout_bicep_curl
    "plank_core", "mountain_climbers", "stretching_cooldown" -> R.drawable.workout_plank_core
    else -> R.drawable.workout_push_up
  }
}

data class Exercise(
  val id: String,
  val name: String,
  val category: String, // Gym or Home
  val muscleGroup: String, // Chest, Back, Legs, Shoulders, Arms, Core, Mobility
  val targetMuscles: String,
  val gifUrl: String,
  val fallbackDescription: String,
  val instructions: List<String>,
  val formTips: String,
  val defaultSets: Int,
  val defaultReps: Int,
  val unit: String = "reps",
  val restSeconds: Int = 60,
  val imageRes: Int = getExerciseImageRes(id)
)

object ExerciseLibrary {
  val allExercises: List<Exercise> = listOf(
    // PUSH-UPS (Core Daily Quest)
    Exercise(
      id = "push_up",
      name = "Daily Push-ups",
      category = "Home",
      muscleGroup = "Chest",
      targetMuscles = "Pectorals, Anterior Deltoids, Triceps",
      gifUrl = "https://media.giphy.com/media/PkR8gPgc2mDlrMSgtu/giphy.gif",
      fallbackDescription = "Classic foundational push-up driving upper body strength.",
      instructions = listOf(
        "Start in a high plank position with hands slightly wider than shoulder-width.",
        "Keep your core rigid and spine in a neutral line from head to heels.",
        "Lower your chest until it is 2 inches above the ground, elbows at a 45-degree angle.",
        "Press powerfully through your palms to return to the locked top position."
      ),
      formTips = "Keep elbows tucked at 45 degrees; avoid flaring them outwards.",
      defaultSets = 4,
      defaultReps = 20,
      restSeconds = 60
    ),
    Exercise(
      id = "knee_push_up",
      name = "Knee Push-ups (Easier Mod)",
      category = "Home",
      muscleGroup = "Chest",
      targetMuscles = "Pectorals, Anterior Deltoids, Triceps",
      gifUrl = "https://media.giphy.com/media/wA8x9b6WzKzlm/giphy.gif",
      fallbackDescription = "Modified push-up on knees for beginners developing strength.",
      instructions = listOf(
        "Rest on your knees with ankles crossed and hands beneath shoulders.",
        "Maintain a straight line from knees to head.",
        "Lower chest smoothly to the floor and push back up."
      ),
      formTips = "Engage your core to avoid arching your lower back.",
      defaultSets = 3,
      defaultReps = 12,
      restSeconds = 45
    ),

    // WARMUP & STRETCHING
    Exercise(
      id = "warmup_mobility",
      name = "Hunter Dynamic Warm-up",
      category = "Both",
      muscleGroup = "Mobility",
      targetMuscles = "Full Body Joint Mobility & Core Activation",
      gifUrl = "https://media.giphy.com/media/3o7TKMt1VVNkHV2PaE/giphy.gif",
      fallbackDescription = "Arm circles, hip openers, and leg swings to prime your muscles.",
      instructions = listOf(
        "Perform 15 forward and backward arm circles.",
        "Do 10 hip rotations in each direction.",
        "Execute 10 thoracic spine twists to awaken the posterior chain."
      ),
      formTips = "Move fluidly with control without forcing painful ranges of motion.",
      defaultSets = 1,
      defaultReps = 5,
      unit = "mins",
      restSeconds = 30
    ),
    Exercise(
      id = "stretching_cooldown",
      name = "Full Body Cooldown Stretch",
      category = "Both",
      muscleGroup = "Mobility",
      targetMuscles = "Hamstrings, Hip Flexors, Chest & Lats",
      gifUrl = "https://media.giphy.com/media/xT0xeJpnrWC4XWblEk/giphy.gif",
      fallbackDescription = "Deep static stretches to accelerate recovery and reduce muscle soreness.",
      instructions = listOf(
        "Hold child's pose for 45 seconds focusing on deep diaphragmatic breaths.",
        "Hold cobra stretch for 30 seconds to open abdominal wall.",
        "Perform seated hamstring stretch 30 seconds per leg."
      ),
      formTips = "Never bounce during static stretching; breathe deeply into tension.",
      defaultSets = 1,
      defaultReps = 5,
      unit = "mins",
      restSeconds = 0
    ),

    // GYM EXERCISES
    Exercise(
      id = "barbell_bench_press",
      name = "Barbell Bench Press",
      category = "Gym",
      muscleGroup = "Chest",
      targetMuscles = "Pectoralis Major, Triceps, Anterior Deltoids",
      gifUrl = "https://media.giphy.com/media/5wFS6a1PE62lKUWXyx/giphy.gif",
      fallbackDescription = "The quintessential compound chest press on a flat bench.",
      instructions = listOf(
        "Lie flat on bench with eyes directly under the bar and feet planted firmly.",
        "Grip slightly wider than shoulder-width; pull shoulder blades retracted and down.",
        "Unrack and lower the bar under control to mid-chest level.",
        "Drive the bar upwards dynamically without lifting hips off the bench."
      ),
      formTips = "Keep wrists stacked straight over elbows and maintain an arch in upper back.",
      defaultSets = 4,
      defaultReps = 10,
      restSeconds = 90
    ),
    Exercise(
      id = "incline_dumbbell_press",
      name = "Incline Dumbbell Press",
      category = "Gym",
      muscleGroup = "Chest",
      targetMuscles = "Clavicular Pectoralis, Anterior Deltoids, Triceps",
      gifUrl = "https://media.giphy.com/media/l41JRsph73VokN6ik/giphy.gif",
      fallbackDescription = "Upper chest builder using dumbbells on a 30-degree incline.",
      instructions = listOf(
        "Sit on an incline bench inclined to 30-45 degrees.",
        "Kick dumbbells up to shoulder level and press upward with palms facing outward.",
        "Lower weights until elbows reach 90 degrees, feeling a deep upper chest stretch.",
        "Squeeze chest at the top without slamming dumbbells together."
      ),
      formTips = "Avoid setting bench too high to prevent anterior deltoid dominance.",
      defaultSets = 3,
      defaultReps = 12,
      restSeconds = 75
    ),
    Exercise(
      id = "lat_pulldown",
      name = "Lat Pulldown",
      category = "Gym",
      muscleGroup = "Back",
      targetMuscles = "Latissimus Dorsi, Biceps, Rhomboids",
      gifUrl = "https://media.giphy.com/media/26AHONQ79FdWZhAI0/giphy.gif",
      fallbackDescription = "Wide-grip vertical pull building upper back width and V-taper.",
      instructions = listOf(
        "Adjust knee pad snug against thighs. Grasp wide bar with overhand grip.",
        "Lean back slightly (10-15 degrees) with chest lifted high.",
        "Pull the bar down toward upper collarbone, driving elbows down and back.",
        "Control the bar back to full stretch at top."
      ),
      formTips = "Initiate the movement by depressing scapulae, not by pulling with forearms.",
      defaultSets = 4,
      defaultReps = 12,
      restSeconds = 75
    ),
    Exercise(
      id = "seated_cable_row",
      name = "Seated Cable Row",
      category = "Gym",
      muscleGroup = "Back",
      targetMuscles = "Middle Trapezius, Rhomboids, Latissimus Dorsi, Biceps",
      gifUrl = "https://media.giphy.com/media/3o7TKR1bWv8W4uE98s/giphy.gif",
      fallbackDescription = "Horizontal row developing back thickness and posture.",
      instructions = listOf(
        "Sit with knees slightly bent and feet secured on platform.",
        "Grip V-bar handle with arms extended and torso upright.",
        "Pull handle toward belly button, squeezing shoulder blades together.",
        "Pause for 1 second, then release smoothly."
      ),
      formTips = "Do not swing your lower back back and forth; keep torso stable.",
      defaultSets = 3,
      defaultReps = 12,
      restSeconds = 60
    ),
    Exercise(
      id = "barbell_squat",
      name = "Barbell Back Squat",
      category = "Gym",
      muscleGroup = "Legs",
      targetMuscles = "Quadriceps, Glutes, Hamstrings, Erector Spinae",
      gifUrl = "https://media.giphy.com/media/3oKIPnAiaMCws8nOsE/giphy.gif",
      fallbackDescription = "The king of lower body power exercises.",
      instructions = listOf(
        "Position bar securely on upper traps. Step out with feet shoulder-width.",
        "Take a deep breath into diaphragm and brace core like preparing for impact.",
        "Break at hips and knees simultaneously, descending until thighs are parallel.",
        "Drive through mid-foot to stand up aggressively."
      ),
      formTips = "Keep knees tracking directly in line with your toes.",
      defaultSets = 4,
      defaultReps = 8,
      restSeconds = 120
    ),
    Exercise(
      id = "leg_press",
      name = "45-Degree Leg Press",
      category = "Gym",
      muscleGroup = "Legs",
      targetMuscles = "Quadriceps, Gluteus Maximus, Hamstrings",
      gifUrl = "https://media.giphy.com/media/26FmRaWfJ9kG8dEGY/giphy.gif",
      fallbackDescription = "Heavy leg volume builder with guided stability.",
      instructions = listOf(
        "Sit securely with back flat against backrest and feet shoulder-width on platform.",
        "Release safety pins and lower the weight until knees reach 90 degrees.",
        "Press platform upward using quadriceps and glutes.",
        "Do not lock out knees abruptly at the apex."
      ),
      formTips = "Never allow lower back to lift or round off the pad.",
      defaultSets = 3,
      defaultReps = 12,
      restSeconds = 90
    ),
    Exercise(
      id = "overhead_shoulder_press",
      name = "Overhead Dumbbell Press",
      category = "Gym",
      muscleGroup = "Shoulders",
      targetMuscles = "Anterior & Lateral Deltoids, Triceps",
      gifUrl = "https://media.giphy.com/media/l41JRsph73VokN6ik/giphy.gif",
      fallbackDescription = "Vertical pressing movement building broad, powerful shoulders.",
      instructions = listOf(
        "Sit or stand upright with dumbbells at shoulder height, palms forward.",
        "Brace core and press weights overhead until arms are extended.",
        "Lower dumbbells steadily to ear level under tension."
      ),
      formTips = "Avoid arching lumbar spine by squeezing glutes and core.",
      defaultSets = 4,
      defaultReps = 10,
      restSeconds = 75
    ),
    Exercise(
      id = "bicep_curl_db",
      name = "Dumbbell Bicep Curls",
      category = "Gym",
      muscleGroup = "Arms",
      targetMuscles = "Biceps Brachii, Brachialis",
      gifUrl = "https://media.giphy.com/media/3o7TKDkAZPG49AOUFi/giphy.gif",
      fallbackDescription = "Direct bicep isolation with controlled supination.",
      instructions = listOf(
        "Hold dumbbells at sides with palms neutral.",
        "Curl weights while supinating palms facing up at the top.",
        "Squeeze peak contraction for 1 second, then lower slowly for 2 seconds."
      ),
      formTips = "Keep elbows pinned at your ribcage; do not rock forward.",
      defaultSets = 3,
      defaultReps = 12,
      restSeconds = 60
    ),
    Exercise(
      id = "tricep_rope_pushdown",
      name = "Cable Tricep Pushdown",
      category = "Gym",
      muscleGroup = "Arms",
      targetMuscles = "Triceps Lateral & Medial Heads",
      gifUrl = "https://media.giphy.com/media/26AHONQ79FdWZhAI0/giphy.gif",
      fallbackDescription = "Targeted arm lockout exercise utilizing cable resistance.",
      instructions = listOf(
        "Stand facing high cable with rope attachment.",
        "Keep elbows tight against torso, press rope downward until arms lock.",
        "Spread rope ends slightly apart at the bottom for maximal squeeze."
      ),
      formTips = "Only forearms should move; keep upper arms motionless.",
      defaultSets = 3,
      defaultReps = 15,
      restSeconds = 60
    ),

    // HOME EXERCISES (Bodyweight / Household alternatives)
    Exercise(
      id = "bodyweight_squat",
      name = "Deep Bodyweight Squats",
      category = "Home",
      muscleGroup = "Legs",
      targetMuscles = "Quadriceps, Glutes, Hamstrings, Core",
      gifUrl = "https://media.giphy.com/media/3oKIPnAiaMCws8nOsE/giphy.gif",
      fallbackDescription = "Fundamental lower body strength builder requiring zero gear.",
      instructions = listOf(
        "Stand with feet shoulder-width apart, toes turned slightly out.",
        "Extend arms forward for counter-balance.",
        "Sit back into hips and bend knees until thighs break parallel.",
        "Push through heels to return to standing position."
      ),
      formTips = "Keep chest upright and knees pushed out over toes.",
      defaultSets = 4,
      defaultReps = 25,
      restSeconds = 60
    ),
    Exercise(
      id = "walking_lunges",
      name = "Walking Lunges",
      category = "Home",
      muscleGroup = "Legs",
      targetMuscles = "Quadriceps, Gluteus Medius, Calves",
      gifUrl = "https://media.giphy.com/media/26FmRaWfJ9kG8dEGY/giphy.gif",
      fallbackDescription = "Unilateral leg builder testing balance and leg stamina.",
      instructions = listOf(
        "Take a large step forward with your right leg.",
        "Lower hips until both knees form 90-degree angles.",
        "Push off front foot and step through directly into the next lunge."
      ),
      formTips = "Keep front knee stacked directly over ankle, not past toes.",
      defaultSets = 3,
      defaultReps = 20,
      restSeconds = 60
    ),
    Exercise(
      id = "chair_dips",
      name = "Chair / Bench Dips",
      category = "Home",
      muscleGroup = "Arms",
      targetMuscles = "Triceps, Anterior Deltoids, Pectorals",
      gifUrl = "https://media.giphy.com/media/5wFS6a1PE62lKUWXyx/giphy.gif",
      fallbackDescription = "Triceps and chest builder using any sturdy chair or couch.",
      instructions = listOf(
        "Place hands on the edge of a sturdy chair behind your back.",
        "Extend legs forward with heels on the floor.",
        "Lower hips by bending elbows to 90 degrees.",
        "Press up forcefully through palms back to start."
      ),
      formTips = "Keep back close to the chair edge to prevent shoulder strain.",
      defaultSets = 3,
      defaultReps = 15,
      restSeconds = 60
    ),
    Exercise(
      id = "doorframe_rows",
      name = "Inverted Table / Door Rows",
      category = "Home",
      muscleGroup = "Back",
      targetMuscles = "Rhomboids, Lats, Rear Deltoids, Biceps",
      gifUrl = "https://media.giphy.com/media/3o7TKR1bWv8W4uE98s/giphy.gif",
      fallbackDescription = "Bodyweight horizontal pulling movement targeting back posture.",
      instructions = listOf(
        "Grip the inside frame of a door or underneath a sturdy table edge.",
        "Lean back so arms are straight and body is inclined at 45 degrees.",
        "Pull your chest to the frame by contracting your shoulder blades.",
        "Lower under control for 2 seconds."
      ),
      formTips = "Maintain a rigid plank throughout your torso and glutes.",
      defaultSets = 4,
      defaultReps = 12,
      restSeconds = 60
    ),
    Exercise(
      id = "pike_pushups",
      name = "Pike Push-ups",
      category = "Home",
      muscleGroup = "Shoulders",
      targetMuscles = "Deltoids, Upper Chest, Triceps",
      gifUrl = "https://media.giphy.com/media/l41JRsph73VokN6ik/giphy.gif",
      fallbackDescription = "Vertical pushing builder replacing gym overhead presses.",
      instructions = listOf(
        "Start in a downward dog yoga posture with hips high in the air.",
        "Look between your hands, lower the top of your head towards the floor.",
        "Press back up through shoulders to the inverted V position."
      ),
      formTips = "The more vertical your torso, the more deltoid load is generated.",
      defaultSets = 3,
      defaultReps = 10,
      restSeconds = 60
    ),
    Exercise(
      id = "plank_core",
      name = "Hunter Core Plank",
      category = "Home",
      muscleGroup = "Core",
      targetMuscles = "Transverse Abdominis, Rectus Abdominis, Obliques",
      gifUrl = "https://media.giphy.com/media/PkR8gPgc2mDlrMSgtu/giphy.gif",
      fallbackDescription = "Isometric powerhouse for unbreakable core stability.",
      instructions = listOf(
        "Rest forearms on floor with elbows beneath shoulders.",
        "Extend legs back, tuck pelvis and contract abdominals and glutes maximally.",
        "Hold with steady shallow breathing."
      ),
      formTips = "Do not allow hips to sag or pike up into the air.",
      defaultSets = 3,
      defaultReps = 60,
      unit = "sec",
      restSeconds = 45
    ),
    Exercise(
      id = "mountain_climbers",
      name = "Agility Mountain Climbers",
      category = "Home",
      muscleGroup = "Core",
      targetMuscles = "Core, Hip Flexors, Shoulders, Cardiovascular",
      gifUrl = "https://media.giphy.com/media/PkR8gPgc2mDlrMSgtu/giphy.gif",
      fallbackDescription = "Rapid alternating knee drives igniting metabolic burn.",
      instructions = listOf(
        "Hold high push-up plank with hands under shoulders.",
        "Drive right knee towards chest, then quickly switch to left knee.",
        "Maintain rapid cadence while keeping hips level."
      ),
      formTips = "Avoid bouncing hips up high; maintain a flat table-top back.",
      defaultSets = 3,
      defaultReps = 30,
      unit = "reps",
      restSeconds = 45
    )
  )

  fun getById(id: String): Exercise? {
    return allExercises.find { it.id == id } ?: allExercises.firstOrNull()
  }
}
