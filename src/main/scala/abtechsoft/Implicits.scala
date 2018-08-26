package abtechsoft

import org.apache.kafka.streams.KeyValue
import org.apache.kafka.streams.kstream.{KeyValueMapper, Reducer, ValueMapper}

import scala.language.implicitConversions

object Implicits {
  implicit def BinaryFunctionToReducer[V](f: ((V, V) => V)): Reducer[V] = (l: V, r: V) => f(l, r)

  implicit class UnaryValueMapper[V, VR](val f: V => VR) extends AnyVal {
    def asValueMapper: ValueMapper[V, VR] = (l: V) => f(l)
  }

  implicit def BinaryKeyValueMapper[K, V, VR](f: (K, V) => VR): KeyValueMapper[K, V, VR] = (k: K, v: V) => f(k, v)

  implicit def Tuple2ToKeyValue[K, V](tuple: (K, V)): KeyValue[K, V] = new KeyValue(tuple._1, tuple._2)

  implicit def valueMapper[V, VR](f: V => VR): ValueMapper[V, VR] = (value: V) => f(value)
}
