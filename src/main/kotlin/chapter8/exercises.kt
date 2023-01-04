package chapter8

interface Prop {
    fun check(): Boolean
    fun and(p: Prop): Prop {
        val checked = this.check() && p.check()
        return object : Prop {
            override fun check() = checked
        }
    }
}
