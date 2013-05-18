# Serializing JavaScript Object Graphs with Jackson

This is a plugin for Jackson which can serialize cyclic object graphs in the [JSOG format](https://github.com/stickfigure/jsog). At the moment it can only serialize, not deserialize, but this is adequate for sending data to JavaScript clients.

To use this plugin, annotate any classes which may contain references with *@JsonIdentityInfo(generator=JSOGGenerator.class)*. Unfortunately Jackson does not currently provide any way to globally enable this feature.

    @JsonIdentityInfo(generator=JSOGGenerator.class)
    public class Person {
        String name;
        Person secretSanta;
    }

## Author

* Jeff Schnitzer (jeff@infohazard.org)
