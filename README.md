Implementing Architecture Katas

Software architecture katas are an approach to explore different architecture design strategies within the framework of a synthetic business problem. Popularized by Neal Ford, a kata is a brief, broad statement of requirements, business goals and constraints. They enable students, typically small groups of developers and architects, to propose different architectural approaches, and analyze and discuss the inevitable trade-offs that impact system qualities.

Expanding on an existing architecture kata, the recent book “Software Architecture: The Hard Parts” has created a more detailed architecture saga. The Sysops saga describes the features of a monolithic application, including user roles, workflows, architecture components and data models. This saga is used to illustrate how to use various patterns, techniques, and trade-off analyses to evolve the application into a distributed, microservices based architecture. This concrete example is a highly effective mechanism to convey ‘architecture thinking’.

The Sysops saga, while detailed, is described at a conceptual level. The architecture evolution is depicted using architecture diagrams with accompanying text to convey the rationales and discuss trade-offs. In our experience, this mirrors the dominant approach used in University and professional courses on software architecture.

Conceptual designs however are not the end point in real systems — deployable code is. This means there is a gap that aspiring software architects must somehow overcome in learning to design and build systems. Mapping conceptual designs to real software platforms and languages is rarely a straightforward exercise, and one we feel should be front and center in the education of a software architect.

For this reason, we are working on a project to provide example implementations of several architecture katas. These can provide a starting point for:

1. educators who wish to create course materials that illustrate conceptual design concepts with concrete code examples.
1. developers who want a sandbox to experiment with and evaluate architecture trade-offs by making code changes and measuring and evaluating the results

You can find our evolving example implementations here in this repos. 

In the article linked to below, we briefly describe our initial implementation of the "Agile Dead Trees!" kata
https://medium.com/@i.gorton/agile-dead-trees-monolith-architecture-kata-implementation-in-spring-9aa6959e27da?sk=10581cee5b2e97bd377640a67d1a3104
