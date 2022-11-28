package model.terrain.rules

abstract class AbstractRule {
    abstract val ruleType: RuleType
    abstract fun action()
}