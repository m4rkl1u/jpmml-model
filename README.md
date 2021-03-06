JPMML-Model [![Build Status](https://travis-ci.org/jpmml/jpmml-model.png?branch=master)](https://travis-ci.org/jpmml/jpmml-model)
===========

Java Class Model API for Predictive Model Markup Language (PMML).

# Features #

* Full support for PMML 3.0, 3.1, 3.2, 4.0, 4.1, 4.2 and 4.3 schemas:
  * Schema version annotations.
  * Extension elements, attributes, enum values.
* Enhanced API:
  * Class hierarchy.
  * Marker interfaces for common traits.
  * Value constructors.
  * Method chaining-friendly setter methods.
  * Optional SAX Locator information.
* [Visitor pattern](http://en.wikipedia.org/wiki/Visitor_pattern):
  * Validation agents.
  * Optimization and transformation agents.
* Supported platforms:
  * Java SE and EE.
  * Android.
  * Google Web Toolkit (GWT).
* Supported JAXB runtimes:
  * [GlassFish Metro](https://metro.java.net)
  * [EclipseLink MOXy](https://www.eclipse.org/eclipselink)
* Supported SerDe runtimes:
  * [FasterXML Jackson](https://github.com/FasterXML/jackson)

# Prerequisites #

* Java 1.8 or newer.

# Installation #

JPMML-Model library JAR files (together with accompanying Java source and Javadocs JAR files) are released via [Maven Central Repository](http://repo1.maven.org/maven2/org/jpmml/).

The current version is **1.4.6** (12 August, 2018).

```xml
<dependency>
	<groupId>org.jpmml</groupId>
	<artifactId>pmml-model</artifactId>
	<version>1.4.6</version>
</dependency>
```

# Usage #

The class model consists of two types of classes. There is a small number of manually crafted classes that are used for structuring the class hierarchy. They are permanently stored in the Java sources directory `/pmml-model/src/main/java`. Additionally, there is a much greater number of automatically generated classes that represent actual PMML elements. They can be found in the generated Java sources directory `/pmml-model/target/generated-sources/xjc` after a successful build operation.

All class model classes descend from class `org.dmg.pmml.PMMLObject`. Additional class hierarchy levels, if any, represent common behaviour and/or features. For example, all model classes descend from class `org.dmg.pmml.Model`.

The class model should be self-explanatory. The application developer is advised to consult with the latest [PMML specification](http://dmg.org/pmml/v4-3/GeneralStructure.html) about the specifics of individual PMML elements and attributes.

### Unmarshalling ###

Loading any PMML schema version 3.X or 4.X document into live `org.dmg.pmml.PMML` instance:

```java
public PMML load(InputStream is) throws SAXException, JAXBException {
  return org.jpmml.model.PMMLUtil.unmarshal(is);
}
```

**Important**: It is the responsibility of the application developer to ensure that the XML document does not contain malicious content (eg. XEE and XXE attacks).

### Applying visitors ###

Deleting SAX Locator information from the class model:

```java
public void optimize(PMML pmml){
  LocatorNullifier nullifier = new LocatorNullifier();
  nullifier.applyTo(pmml);
}
```

### Marshalling ###

Storing live `org.dmg.pmml.PMML` instance into PMML schema version 4.3 document:

```java
public void store(PMML pmml, OutputStream os) throws JAXBException {
  org.jpmml.model.PMMLUtil.marshal(pmml, os);
}
```

# Example applications #

Module `pmml-model-example` exemplifies the use of JPMML-Model library.

This module can be built using [Apache Maven](http://maven.apache.org/):
```
mvn clean install
```

The resulting uber-JAR file `target/example-1.4-SNAPSHOT.jar` contains the following command-line applications:
* `org.jpmml.model.CopyExample` [(source)](https://github.com/jpmml/jpmml-model/blob/master/pmml-model-example/src/main/java/org/jpmml/model/CopyExample.java). Copies and transforms a PMML schema version 3.X or 4.X document to a PMML schema version 4.3 document.
* `org.jpmml.model.ObfuscationExample` [(source)](https://github.com/jpmml/jpmml-model/blob/master/pmml-model-example/src/main/java/org/jpmml/model/ObfuscationExample.java). Obfuscates a PMML document by replacing field names with their MD5 hashes.
* `org.jpmml.model.TranslationExample` [(source)](https://github.com/jpmml/jpmml-model/blob/master/pmml-model-example/src/main/java/org/jpmml/model/TranslationExample.java). Translates a PMML document to a JSON or YAML document.
* `org.jpmml.model.ValidationExample` [(source)](https://github.com/jpmml/jpmml-model/blob/master/pmml-model-example/src/main/java/org/jpmml/model/ValidationExample.java). Validates a PMML document against the built-in XML Schema Definition (XSD) resource.

Copying `input.pmml` to `output.pmml`; the class model is transformed by applying a list of visitors to it:
```
java -javaagent:../pmml-agent/target/pmml-agent-1.4-SNAPSHOT.jar -cp target/example-1.4-SNAPSHOT.jar org.jpmml.model.CopyExample --visitor-classes org.jpmml.model.visitors.DictionaryCleaner,org.jpmml.model.visitors.MiningSchemaCleaner --summary true --input input.pmml --output output.pmml
```

Checking the validity of `model.pmml`:
```
java -cp target/example-1.4-SNAPSHOT.jar org.jpmml.model.ValidationExample --input model.pmml
```

Getting help:
```
java -cp target/example-1.4-SNAPSHOT.jar <application class name> --help
```

It is possible to activate a specific Java XML Binding (JAXB) runtime by setting the value of the `javax.xml.bind.context.factory` system property. Use `com.sun.xml.bind.v2.ContextFactory` for activating a GlassFish Metro runtime, and `org.eclipse.persistence.jaxb.JAXBContextFactory` for activating an EclipseLink MOXy runtime.

For example:
```
java -Djavax.xml.bind.context.factory=org.eclipse.persistence.jaxb.JAXBContextFactory -cp target/example-1.4-SNAPSHOT.jar ...
```

# Support and Documentation #

Limited public support is available via the [JPMML mailing list](https://groups.google.com/forum/#!forum/jpmml).

# License #

JPMML-Model is licensed under the [BSD 3-Clause License](http://opensource.org/licenses/BSD-3-Clause).

# Additional information #

JPMML-Model is developed and maintained by Openscoring Ltd, Estonia.

Interested in using JPMML software in your application? Please contact [info@openscoring.io](mailto:info@openscoring.io)
