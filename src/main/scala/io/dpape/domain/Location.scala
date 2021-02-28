package io.dpape.domain

case class Location(latitude: Double, longitude: Double) {
  def toGeoString = s"${latitude}, ${longitude}"
}