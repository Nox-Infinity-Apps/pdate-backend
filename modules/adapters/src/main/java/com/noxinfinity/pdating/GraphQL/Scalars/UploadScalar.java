package com.noxinfinity.pdating.GraphQL.Scalars;

import com.netflix.graphql.dgs.DgsScalar;
import graphql.schema.Coercing;
import graphql.schema.CoercingParseLiteralException;
import graphql.schema.CoercingParseValueException;
import graphql.schema.CoercingSerializeException;
import org.springframework.web.multipart.MultipartFile;

@DgsScalar(name = "Upload")
public class UploadScalar implements Coercing<MultipartFile, String> {

    @Override
    public String serialize(Object dataFetcherResult) throws CoercingSerializeException {
        throw new CoercingSerializeException("Upload serialization is not supported");
    }

    @Override
    public MultipartFile parseValue(Object input) throws CoercingParseValueException {
        if (input instanceof MultipartFile) {
            return (MultipartFile) input;
        } else {
            throw new CoercingParseValueException("Expected a MultipartFile");
        }
    }

    @Override
    public MultipartFile parseLiteral(Object input) throws CoercingParseLiteralException {
        throw new CoercingParseLiteralException("Parsing literals for Upload is not supported");
    }

}
