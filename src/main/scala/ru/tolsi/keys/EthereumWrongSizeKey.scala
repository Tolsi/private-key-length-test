package ru.tolsi.keys

import org.ethereum.crypto.ECKey

object EthereumWrongSizeKey extends App {
  val pk = Array.fill(40)(0.toByte)
  val data = Array.fill(32)(0.toByte)
  val ecKey = ECKey.fromPrivate(pk)
  val signature = ecKey.doSign(data)
  require(ecKey.verify(data, signature))
}
