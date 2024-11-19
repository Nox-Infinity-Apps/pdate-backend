package com.noxinfinity.pdating.GraphQL.Scalars;

import com.netflix.graphql.dgs.DgsScalar;
import graphql.language.StringValue;
import graphql.schema.Coercing;
import graphql.schema.CoercingParseLiteralException;
import graphql.schema.CoercingParseValueException;
import graphql.schema.CoercingSerializeException;

import java.util.UUID;

@DgsScalar(name = "UUID")
public class UUIDScalar implements Coercing<UUID, String> {
    @Override
    public String serialize(Object dataFetcherResult) throws CoercingSerializeException {
        if (dataFetcherResult instanceof UUID) {
            return dataFetcherResult.toString();
        } else {
            throw new CoercingSerializeException("Not a valid UUID");
        }
    }

    @Override
    public UUID parseValue(Object input) throws CoercingParseValueException {
        try {
            return UUID.fromString(input.toString());
        } catch (IllegalArgumentException e) {
            throw new CoercingParseValueException("Invalid UUID format");
        }
    }

    @Override
    public UUID parseLiteral(Object input) throws CoercingParseLiteralException {
        if (input instanceof StringValue) {
            try {
                return UUID.fromString(((StringValue) input).getValue());
            } catch (IllegalArgumentException e) {
                throw new CoercingParseLiteralException("Value is not a valid UUID");
            }
        }
        throw new CoercingParseLiteralException("Value is not a valid UUID literal");
    }
}
