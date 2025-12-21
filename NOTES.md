- Sooooo I'm probably gonna make grades and patterns data-driven...
  - to this end, the block should be able to dynamically switch models, each with several colour layers.
# Models 101
ModelTemplate#createWithSuffix or #create creates a ResourceLocation pointing to a model id based
on the registry id of the block that is passed to it. A suffix may be appended after to create a
ResourceLocation of the block's registry id + passed suffix.

A ModelTemplate works primarily based around the BiConsumer argument in the aforementioned methods.
The ModelTemplate holds a reference to the id of the parent model, then, when the BiConsumer (the
model outputter from the BlockModelGenerators) is passed in, it generates a model, automatically
filling in the texture variables specified inside the ModelTemplate according to the TextureMapping
passed in.

The model is generated, and the ResourceLocation pointing to it can then be used to generate the
BlockState data. BlockModelGenerators work by creating models, and then creating BlockStates to which
the models are assigned.