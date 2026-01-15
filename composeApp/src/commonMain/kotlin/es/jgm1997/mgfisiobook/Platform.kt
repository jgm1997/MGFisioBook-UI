package es.jgm1997.mgfisiobook

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform