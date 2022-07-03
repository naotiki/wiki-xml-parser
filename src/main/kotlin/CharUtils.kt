fun Char.toHiraganaOrNull(): Char? {

    if (this in 'ぁ'..'ん') {
        return this
    }
    if (this in 'ァ'..'ン') {
       return (this-96)
    }
    return null
}