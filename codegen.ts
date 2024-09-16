import type { CodegenConfig } from '@graphql-codegen/cli';

const config: CodegenConfig = {
    generates: {
        './graphql-gen.yaml': {
            config: {
                strictScalars: true,
            },
        },
    },
};
export default config;
