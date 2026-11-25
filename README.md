# Spring Data JPA Örnekleri

Spring Boot ve Spring Data JPA ile ilişkili entity'lerin yüklenmesini (cascade, `@EntityGraph`, `JOIN FETCH`) ve
`Specification` ile dinamik filtrelemeyi gösteren örnek proje.

> **Not:** Bu depo 2017-2018 yıllarında eğitim/örnek amaçlı yazıldı ve artık güncellenmiyor. Bağımlılıkları
> eskidir ve bilinen güvenlik açıkları içerir; üretimde kullanmayın.

## İçerik

`springdata/src/main/java/com/mustafaergan/springdata` altında:

| Paket | Ne var |
|---|---|
| `entity` | `Person` ve ona `@ManyToMany` ile bağlı `Departmant`, `Customer`; ayrıca `Address`. `Person` üzerinde adlandırılmış entity graph'lar tanımlı |
| `dao` | `PersonDao`: `JpaRepository` + `JpaSpecificationExecutor`; `@EntityGraph` ile ve `JOIN FETCH` sorgusuyla ilişkileri birlikte yükleyen metotlar |
| `filter` | `Specification` uygulayan genel `Filter<T>`, `SearchCriteria` (`>`, `<`, `:` işlemleri) ve `PersonFilter` |
| `service` | `PersonServiceImpl`: anahtara göre getirme, ilişkileriyle getirme, filtreyle listeleme |
| `controller` | `PersonController`: `/person/get`, `/person/getEager`, `/person/getFilterData` uçları; sonucu Gson ile JSON'a çevirir |

## Notlar

### Cascade

| Tür | Anlamı |
|---|---|
| `PERSIST` | Nesne persist edilirse alt nesne de persist edilir |
| `MERGE` | Nesne merge edilirse alt nesne de merge edilir |
| `REMOVE` | Nesne silinirse bağlı alt nesne de silinir |
| `REFRESH` | Nesne yenilenirse bağlı alt nesne de yenilenir |
| `ALL` | Tüm işlemler birlikte yapılır |

### Entity Graph

```java
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

Sorguda hangi ilişkilerin birlikte yüklenmesi isteniyorsa o graph kullanılır:

```java
@EntityGraph(value = "Person.allJoins", type = EntityGraphType.LOAD)
public Person findByPersonId(String id);
```

`EntityGraphType` değerleri:

- `LOAD`: Graph'taki alanlar EAGER yüklenir, graph'ta olmayanlar kendi tanımlarındaki (varsayılan) davranışı korur.
- `FETCH`: Graph'taki alanlar EAGER yüklenir, graph'ta olmayanlar LAZY kabul edilir.

Aynı sonuç `@Query("SELECT t FROM Person t JOIN FETCH t.departmantSet ...")` ile de alınabilir
(`PersonDao.findByPersonIdWithFetch`).

## Teknolojiler

- Java 8, Maven
- Spring Boot 1.5.8 (Data JPA, Jersey), Lombok, Gson

## Çalıştırma

1. `springdata/src/main/resources/application.properties` MySQL'deki `portal` veritabanını kullanacak şekilde
   ayarlı; kullanıcı adı ve şifreyi kendi kurulumunuza göre değiştirin. `ddl-auto=create-drop` olduğu için
   tablolar her açılışta yeniden oluşturulur.
2. `pom.xml` içinde veritabanı sürücüsü yok; çalıştırmadan önce `mysql-connector-java` bağımlılığını eklemeniz gerekir.
3. Ardından:

```bash
cd springdata
mvn spring-boot:run
```
