package com.john.bookmgmt.exception

import graphql.GraphQLException

/**
 * @author yoonho
 * @since 2022.10.26
 */
class GraphQLNotFoundException(msg: String) : GraphQLException(msg)
