# spring-data-jpa

Cascade:

PERSIST	=> Nesne persist edilirse alt nesne de persist edilir

MERGE	=> Nesne merge edilirse alt nesne de merge edilir

REMOVE	=> Nesne silinirse bağlı alt nesne de silinir

REFRESH	=> Nesne yenilenirse bağlı alt nesne de yenilenir

ALL	Tüm => işlemler birlikte yapılır


Entity Graphs:

```
@NamedEntityGraphs({
    @NamedEntityGraph(name="Person.allJoinsButCustomer", attributeNodes = {
            @NamedAttributeNode("departmantSet")
    }),
    @NamedEntityGraph(name="Person.allJoins", attributeNodes = {
            @NamedAttributeNode("departmantSet"),
            @NamedAttributeNode("customerSet")
    }),
    @NamedEntityGraph(name="noJoins", attributeNodes = {
    })
})
```

Sorgu sırasında hangisi join edilecekse o join kullanılabilir:

```
 @EntityGraph(value = "Person.allJoins", type=EntityGraphType.LOAD)
 public Person findByPersonId(String id);
```
EntityGraphType:

EntityGraphType.LOAD => EAGER gibi davranır

EntityGraphType.FETCH => LAZY gibi davranır

