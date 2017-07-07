package caseapp

import caseapp.core.Messages

import org.scalatest._

class HelpTests extends FlatSpec with Matchers {

  import Definitions._

  "case-app" should "generate a help message" in {

    val message = CaseApp.helpMessage[Example]

    val expectedMessage =
      """Example
        |Usage: example [options]
        |  --foo  <value>
        |  --bar  <value>""".stripMargin

    def lines(s: String) = s.lines.toVector

    for (((a, b), idx) <- lines(message).zip(lines(expectedMessage)).zipWithIndex if a != b)
      Console.err.println(s"Line $idx, expected '$b', got '$a'")

    lines(message) shouldBe lines(expectedMessage)
  }

  it should "not add a help message for fields annotated with @Hidden" in {
    case class Options(
      first: Int,
      @Hidden
      second: String
    )

    val helpLines = Messages[Options].helpMessage.lines.toVector

    helpLines.count(_.contains("--first")) shouldBe 1
    helpLines.count(_.contains("--second")) shouldBe 0
  }

}
