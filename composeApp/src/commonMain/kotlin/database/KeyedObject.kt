package fi.tuska.beerclock.database

abstract class KeyedObject(private val identityKey: String) {

    override fun equals(other: Any?): Boolean {
        // Let's go with: to be equal any keyed object must be of the same class and have the same key.
        // This should work for our purposes, for all subclasses.
        // We don't want different subclasses to mix'n'match via equals
        return other is KeyedObject && other::class.isInstance(this) && this::class.isInstance(
            other
        ) && identityKey == other.identityKey
    }

    override fun hashCode(): Int {
        // by definition key must uniquely identify the item and must change if contents change
        // so it works for hashCode as well
        return identityKey.hashCode()
    }

}
