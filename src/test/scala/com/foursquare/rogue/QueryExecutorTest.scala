// Copyright 2011 Foursquare Labs Inc. All Rights Reserved.
package com.foursquare.rogue

import org.junit._
import org.specs.SpecsMatchers
import com.foursquare.rogue.MongoHelpers.{AndCondition, QueryExecutor}
import net.liftweb.mongodb.record.{MongoId, MongoMetaRecord, MongoRecord}

class QueryExecutorTest extends SpecsMatchers {

  class Dummy extends MongoRecord[Dummy] with MongoId[Dummy] {
    def meta = Dummy
  }

  object Dummy extends Dummy with MongoMetaRecord[Dummy] {
  }

  @Test
  def testExeptionInRunCommandIsDecorated {
    val query = BasicQuery[Dummy, Dummy, Unordered, Unselected, Unlimited, Unskipped, HasNoOrClause](
      Dummy, None, None, None, None, None, AndCondition(Nil, None), None, None)
    (QueryExecutor.runCommand(FindQueryCommand(query, None)(_ => ())) {
      throw new RuntimeException("bang")
      "hi"
    }) must throwA[RogueException]
  }

}
