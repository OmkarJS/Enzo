package omkar.android.projects.app.constants

class Constants {
    object Routes {
        const val HOME = "home"
        const val PROFILE = "profile"
        const val VISUAL_SCREEN = "visuals"
        const val BIT_FORGE_SCREEN = "bitforge"
    }

    object Screen {
        const val HOME_PAGE = "Home"
        const val PROFILE_PAGE = "Profile"
        const val SUMMARY_PAGE = "Summary"
    }

    object RemoteConstants {
        const val EXAMPLE_ENDPOINT = "example_endpoint"
    }

    object SdrConstants {
        const val SDR_SIZE = 2048
        const val ACTIVE_BITS = 32

        // Vision
        const val VISION_START = 0
        const val VISION_TOTAL = 768
        const val VISION_ACTIVE_BITS = 30
        const val BRIGHTNESS_BUCKETS = 32
        const val BRIGHTNESS_SEED = 11 /** To generate predictable active bits */
        
        // Audio
        const val AUDIO_START = 768
        const val AUDIO_SIZE = 384
        
        // Touch
        const val TOUCH_START = 1152
        const val TOUCH_SIZE = 384

        // Reference frame
        const val REF_START = 1536
        const val REF_SIZE = 512
        const val REF_ACTIVE_BITS = 20
    }


}