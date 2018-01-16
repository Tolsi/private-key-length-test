package ru.tolsi.keys

import org.ethereum.crypto.ECKey
import org.spongycastle.util.encoders.Hex


object EthereumWrongSizeKey extends App {
  def addressFromPublicKey(pk: Array[Byte]): String = {
    Hex.toHexString(ECKey.fromPublicOnly(pk).getAddress)
  }

  val pk = Hex.decode("e65f46f09a967c2c01d19be33163b2f62e34d8d23d0637b9e063cd3f2f194dcd63f28dc1112c1cf8")
  val data = Array.fill(32)(1.toByte)
  val ecKey = ECKey.fromPrivate(pk)

  println(s"Private key ${pk.length} bytes: ${Hex.toHexString(pk)}")
  println(s"Fake private key (it's not used?) ${ecKey.getPrivKeyBytes.length} bytes: ${Hex.toHexString(ecKey.getPrivKeyBytes)}")
  println(s"Public key ${ecKey.getPubKey.length} bytes: ${Hex.toHexString(ecKey.getPubKey)}")
  println(s"Address: 0x${addressFromPublicKey(ecKey.getPubKey)}")

  val signature = ecKey.sign(data)
  require(ecKey.verify(data, signature))
}
