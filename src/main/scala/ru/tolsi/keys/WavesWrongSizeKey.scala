package ru.tolsi.keys

import java.lang.reflect.Constructor

import org.whispersystems.curve25519.OpportunisticCurve25519Provider
import scorex.crypto.signatures.Curve25519

object WavesWrongSizeKey extends App {
  val provider: OpportunisticCurve25519Provider = {
    val constructor = classOf[OpportunisticCurve25519Provider]
      .getDeclaredConstructors
      .head
      .asInstanceOf[Constructor[OpportunisticCurve25519Provider]]
    constructor.setAccessible(true)
    constructor.newInstance()
  }
  val pk = Array.fill(40)(0.toByte)
  val pubkey = provider.generatePublicKey(pk)
  val data = Array.fill(32)(0.toByte)
  val signature = Curve25519.sign(pk, data)
  require(provider.verifySignature(pubkey, data, signature))
}
