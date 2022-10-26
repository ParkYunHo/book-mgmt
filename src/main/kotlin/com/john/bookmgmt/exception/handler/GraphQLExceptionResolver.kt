package com.john.bookmgmt.exception.handler

import com.john.bookmgmt.exception.GraphQLNotFoundException
import graphql.GraphQLError
import graphql.GraphqlErrorBuilder
import graphql.schema.DataFetchingEnvironment
import org.springframework.graphql.execution.DataFetcherExceptionResolverAdapter
import org.springframework.graphql.execution.ErrorType
import org.springframework.stereotype.Component

@Component
class GraphQLExceptionResolver: DataFetcherExceptionResolverAdapter() {
    override fun resolveToSingleError(ex: Throwable, env: DataFetchingEnvironment): GraphQLError? {
        if(ex is GraphQLNotFoundException){
            return GraphqlErrorBuilder.newError()
                .errorType(ErrorType.NOT_FOUND)
                .message(ex.message)
                .path(env.executionStepInfo.path)
                .location(env.field.sourceLocation)
                .build()
        }

        return null
    }
}