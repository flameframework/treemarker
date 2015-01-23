# treemarker

Treemarker is a wrapper for FreeMarker that lets you generate a whole tree of 
files from a single model. This can be useful for code generation or other 
purposes.

## FreeMarker

FreeMarker lets you 

1. define a template with tags. An example of a tag is `${contact.address}`,
2. provide a model with tag values, for example `contact.setAddress("Foobar 3")` and
3. merge the values into the template, generating a document where 
   `${contact.address}` is replaced by `Foobar 3`.

Full documentation on Freemarker can be found on http://freemarker.org.

## Creating treemarker templates

Treemarker adds functionality to FreeMarker:

1. Instead of merging a model into a single template, you can merge a model into 
   a whole tree of templates.
2. Each template on its own can result in multiple output files.
3. Names of output files may also depend on the values in the model.

To enable this, treemarker adds a single tag:

```
<&output file="${relative.file.path}" overwrite="true">
  ...
</&output>
```

After having been merged by FreeMarker, everything between `<&output>` and 
`</&output>` is written to `${relative.file.path}`. `${relative.file.path}` can
be a literal, but it can also contain FreeMarker variables.  The file path where 
the output will be written is relative to the location of the template. 

> Example:
>
> - the template is located at `folder1/template.txt`,
> - the `file` attribute equals `subfolder/${contact.name}.baz`, and
> - in the model, `contact.name` has the value `bar`.
> 
> In this cases, the output will be written to `folder1/subfolder/bar.baz`.

`overwrite` is an optional value. If overwrite is false, already existing files
will not be overwritten by treemarker. By default files are overwritten by 
treemarker.

Combining FreeMarker tags with the `<&output>` tag can be very powerful. In the
example above, suppose you have a list of contacts. If you nest the `<&output>` 
tag in a `<#list>` tag, you can generate a separate file for each contact in the
list.

## Calling treemarker

Treemarker assumes your input templates are somewhere on the classpath.
This way, you can supply a set of templates as a single jar file. Support for
reading templates from other locations than the classpath is likely to be added
in the future.

You can call treemarker from Scala:

```
import com.github.flameframework.treemarker.TreeMarker

TreeMarker.generate(classpathPrefix, target, dataModel)
```

- classPathPrefix is the root package on the classPath where your templates can 
  be found. A value of `treemarker/templates` would parse everything in the 
  folder `treemarker/templates` and its subfolders. It would suggest that your
  jar looks like this:
  
  treemarker/
  - templates/
    - some_templates
    - folder1/
      - template.txt
    
  etc.
  
- target is the root path where the output will be written

- dataModel is the exact same data model you would pass into FreeMarker. It
  contains all the values that should be merged into the templates.

A Java interface and a possibility to call treemarker directly from the command
line are likely to be added in the future.
