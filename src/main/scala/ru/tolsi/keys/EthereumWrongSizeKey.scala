package ru.tolsi.keys

import org.ethereum.crypto.ECKey

object EthereumWrongSizeKey extends App {
  val pk = 1.toByte +: Array.fill(39)(0.toByte)
  val data = Array.fill(32)(1.toByte)
  val ecKey = ECKey.fromPrivate(pk)
  val signature = ecKey.doSign(data)
  require(ecKey.verify(data, signature))
}
