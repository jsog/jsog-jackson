# Serializing JavaScript Object Graphs with Jackson

This is a plugin for Jackson which can serialize cyclic object graphs in the [JSOG format](https://github.com/stickfigure/jsog). At the moment it can only serialize, not deserialize, but this is adequate for sending data to JavaScript clients.

## Source code

The official repository is (https://github.com/stickfigure/jackson-jsog)

## Download

This plugin is available in Maven Central:

	<dependency>
		<groupId>com.voodoodyune.jackson.jsog</groupId>
		<artifactId>jackson-jsog</artifactId>
		<version>1.0</version>
		<scope>compile</scope>
	</dependency>

It can be downloaded directly from [http://search.maven.org/]

## Usage

To use this plugin, annotate any classes which may contain references with *@JsonIdentityInfo(generator=JSOGGenerator.class)*. Unfortunately Jackson does not currently provide any way to globally enable this feature.

    @JsonIdentityInfo(generator=JSOGGenerator.class)
    public class Person {
        String name;
        Person secretSanta;
    }
    
## Author

* Jeff Schnitzer (jeff@infohazard.org)

## License

This software is provided under the [MIT license](http://opensource.org/licenses/MIT)