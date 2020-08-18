package rationals
import org.w3c.dom.ranges.Range
import java.math.BigInteger
class Rational(val nominator: BigInteger,val denumerator: BigInteger) : Comparable<Rational>
{
    var n: BigInteger
    var d: BigInteger
    init {
        try {
            nominator/denumerator
            var pgcd :BigInteger = nominator.gcd(denumerator)
            if (denumerator>0.toBigInteger())
            {this.n = nominator/pgcd
            this.d = denumerator/pgcd}
            else{
                this.n = -nominator/pgcd
                this.d = -denumerator/pgcd
            }
        }
        catch (e: IllegalArgumentException){
            throw IllegalArgumentException("Zero in denominator")
        }

    }

    override fun compareTo(other: Rational): Int {
        val newThis : Rational = Rational(this.n*other.d,this.d*other.d)
        val newOther :Rational = Rational(other.n*this.d,other.d*this.d)
        return newThis.n.compareTo(newOther.n)
    }

    override fun equals(other: Any?): Boolean {
        if (other is Rational){
            val newThis : Rational = Rational(this.n*other.d,this.d*other.d)
            val newOther :Rational = Rational(other.n*this.d,other.d*this.d)
            return newThis.n.equals(newOther.n)
        }
        else{
            return false
        }
    }

    override fun toString(): String {
        if (this.d!=1.toBigInteger()) return "${this.n}/${this.d}"
        else return "${this.n}"
    }

}
operator fun Rational.rangeTo(other: Rational):Pair<Rational,Rational>{
    return  Pair(this,other)
}
operator fun Pair<Rational,Rational>.contains(element: Rational): Boolean {
    return element.n*this.component1().d*this.component2().d in this.component1().n*element.d*this.component2().d..this.component2().n*element.d*this.component1().d
}
fun <T> T.toRational() : Rational{
    when(this){
        is Int -> return Rational(this.toBigInteger(),1.toBigInteger())
        is String -> {
            var list:List<Char> = this.toList()
            var (nominator,denominator) = this.partition {
                var index :Int = list.indexOf(it)
                var bool:Boolean = index<list.indexOf('/')
                list = list.drop(index)
                bool

            }
            denominator = denominator.filter { it != '/' }
            if (nominator.length == 0) {
                nominator = denominator
                denominator=1.toString()
            }

            return Rational(nominator.toBigInteger(),denominator.toBigInteger())

        }
        else -> return Rational(0.toBigInteger(),0.toBigInteger())
    }
}
operator fun Rational.plus(other: Rational) : Rational{
    return Rational(this.n*other.d+other.n*this.d,this.d*other.d)
}
operator fun Rational.minus(other: Rational) : Rational{
    return Rational(this.n*other.d -other.n*this.d,this.d*other.d)
}
operator fun Rational.times(other: Rational) : Rational{
    return Rational(this.n * (other.n),this.d*other.d)
}
operator fun Rational.div(other: Rational) : Rational{
    return Rational(this.n * (other.d),this.d*other.n)
}
operator fun Rational.unaryMinus() : Rational{
    return Rational(-this.n,this.d)
}
fun main() {
    val half = 1 divBy 2
    val third = 1 divBy 3
    val sum: Rational = half + third
    println(5 divBy 6 == sum)
    val difference: Rational = half - third
    println(1 divBy 6 == difference)
    val product: Rational = half * third

    println(1 divBy 6 == product)

    val quotient: Rational = half / third
    println(3 divBy 2 == quotient)

    val negation: Rational = -half
    println(-1 divBy 2 == negation)

    println((2 divBy 1).toString() == "2")
    println((-2 divBy 4).toString() == "-1/2")
    println("117/1098".toRational().toString() == "13/122")

    val twoThirds = 2 divBy 3
    println(half < twoThirds)

    println(half in third..twoThirds)

    println(2000000000L divBy 4000000000L == 1 divBy 2)

    println("912016490186296920119201192141970416029".toBigInteger() divBy
            "1824032980372593840238402384283940832058".toBigInteger() == 1 divBy 2)
}
infix fun Number.divBy(l: Number): Rational {
 if (this is Int && l is Int) return Rational(this.toBigInteger(),l.toBigInteger())
 else{
     if (this is Long && l is Long) return Rational(this.toBigInteger(),l.toBigInteger())
     else{
         if (this is BigInteger && l is BigInteger) return Rational(this,l)
         else return Rational(0.toBigInteger(),0.toBigInteger())
     }
 }
}

