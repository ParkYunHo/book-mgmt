package com.john.bookmgmt.exception

import graphql.GraphQLException

class GraphQLNotFoundException(msg: String) : GraphQLException(msg)
