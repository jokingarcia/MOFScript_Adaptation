# MOFScript_Adaptation
An adapter-based approach to adapt generated SQL in Model to Text transformations to database schema evolution

The idea is explained in the paper "An adapter-based approach to adapt generated SQL in M2T" ([http://www.onekin.org/content/adapter-based-approach-adapt-generated-sql-model-text-transformations-db-schema-evolution])

Forward Engineering advocates for code to be generated dynamically through model-to-text transformations that target a specific platform. In this setting, platform evolution can leave the transformation, and hence the generated code, outdated. This issue is exacerbated by the perpetual beta phenomenon in Web 2.0 platforms where continuous delta releases are a common practice. Here, manual co-evolution becomes cumbersome. This paper looks at how to automate fully or in part the synchronization process between the platform and the transformation.
To this end, the transformation process is split in two parts: the stable part is coded as a MOFScript transformation whereas the unstable side is isolated through an adapter that is implicitly called by
the transformation at generation time. In this way, platform upgrades impact the adapter but leave the transformation untouched. The work focuses on DB schema evolution, and takes MediaWiki as a vivid case
study. A first case study results in the upfront cost of using the adapter paying off after three releases MediaWiki upgrades.
