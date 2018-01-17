package ru.tolsi.keys

import java.lang.reflect.Constructor

import org.spongycastle.util.encoders.Hex
import org.whispersystems.curve25519.{JavaCurve25519Provider, OpportunisticCurve25519Provider}
import scorex.crypto.encode.Base58
import scorex.crypto.hash.{Blake2b256, Keccak256}

object WavesWrongSizeKey extends App {

  val provider: JavaCurve25519Provider = {
    val constructor = classOf[JavaCurve25519Provider]
      .getDeclaredConstructors
      .head
      .asInstanceOf[Constructor[JavaCurve25519Provider]]
    constructor.setAccessible(true)
    constructor.newInstance()
  }

  val AddressVersion: Byte = 1
  val ChecksumLength = 4
  val HashLength = 20

  def checkSum(withoutChecksum: Array[Byte]): Array[Byte] = hash(withoutChecksum).take(ChecksumLength)

  def hash(bytes: Array[Byte]): Array[Byte] = {
    scorex.utils.HashHelpers.applyHashes(bytes, Blake2b256, Keccak256)
  }

  def addressFromPublicKey(network: Byte, publicKey: Array[Byte]): String = {
    val publicKeyHash = hash(publicKey).take(HashLength)
    val withoutChecksum = AddressVersion +: network +: publicKeyHash
    val bytes = withoutChecksum ++ checkSum(withoutChecksum)
    Base58.encode(bytes)
  }

  val pk = Hex.decode("e65f46f09a967c2c01d19be33163b2f62e34d8d23d0637b9e063cd3f2f194dcd63f28dc1112c1cf8")
  val pubkey = provider.generatePublicKey(pk)

  println(s"Private key ${pk.length} bytes: ${Hex.toHexString(pk)}")
  println(s"Public key ${pubkey.length} bytes: ${Hex.toHexString(pubkey)}")
  println(s"Address: ${addressFromPublicKey('W', pubkey)}")

  val data = Array.fill(32)(1.toByte)
  val signature = provider.calculateSignature(provider.getRandom(64), pk, data)
  require(provider.verifySignature(pubkey, data, signature))
}
