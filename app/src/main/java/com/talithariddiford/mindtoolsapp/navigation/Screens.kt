package com.packt.chapterseven.navigation

sealed class Screens(val route: String) {
    object ActivityToolsPage : Screens("activityTools")
    object AddActivityPage : Screens("addActivity")
    object AddFilterByMoodPage: Screens("addFilterByMoodPage")
    object AddLinkVideoCreationPage: Screens("addLinkVideoCreationPage")
    object AddPhoneCallCreationPage : Screens("AddphoneCallPage")
    object AddPdfCreationPage : Screens("AddpdfPage")




}

