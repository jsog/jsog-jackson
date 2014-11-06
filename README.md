# JavaScript Object Graphs with Jackson

This is a plugin for Jackson which can serialize cyclic object graphs in the [JSOG format](https://github.com/jsog/jsog). At the moment it can only serialize, not deserialize, but this is adequate for sending data to JavaScript clients.

## Source code

The official repository is (https://github.com/jsog/jsog-jackson)

## Download

This plugin is available in Maven Central:

	<dependency>
		<groupId>com.voodoodyne.jackson.jsog</groupId>
		<artifactId>jackson-jsog</artifactId>
		<version>please look up latest version</version>
		<scope>compile</scope>
	</dependency>

It can be downloaded directly from [http://search.maven.org/]

## Usage

To use this plugin, annotate any classes which may contain references with *@JsonIdentityInfo(generator=JSOGGenerator.class)*.

    @JsonIdentityInfo(generator=JSOGGenerator.class)
    public class Person {
        String name;
        Person secretSanta;
    }
    
## Author

* Jeff Schnitzer (jeff@infohazard.org)

## License

This software is provided under the [MIT license](http://opensource.org/licenses/MIT)
