package com.pro.blog.config

import com.netflix.graphql.dgs.DgsComponent
import com.netflix.graphql.dgs.DgsRuntimeWiring
import graphql.scalars.ExtendedScalars
import graphql.schema.idl.RuntimeWiring

@DgsComponent
class Scalar {
    @DgsRuntimeWiring
    fun longScalar(builder: RuntimeWiring.Builder): RuntimeWiring.Builder? {
        return builder.scalar(ExtendedScalars.GraphQLLong)
    }

    @DgsRuntimeWiring
    fun objScalar(builder: RuntimeWiring.Builder): RuntimeWiring.Builder? {
        return builder.scalar(ExtendedScalars.Object)
    }
}