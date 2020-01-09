# Skyline Dispatcher Configuration

The folder [src](../src) contains an initial Skyline Dispatcher Configuration. In order to use
that configuration in your environment, copy that folder to your AEM application's `dispatcher`
folder.

If you're converting AMS dispatcher configuration files, there is a manual in
[How to convert an AMS to a Skyline dispatcher configuration](./TransitionFromAMS.md)

## Configuration structure

```
./
├── conf.d
│   ├── available_vhosts
│   │   └── default.vhost
│   ├── dispatcher_vhost.conf
│   ├── enabled_vhosts
│   │   ├── README
│   │   └── default.vhost -> ../available_vhosts/default.vhost
│   └── rewrites
│   │   ├── default_rewrite.rules
│   │   └── rewrite.rules
│   └── variables
|       ├── custom.vars
│       └── global.vars
└── conf.dispatcher.d
    ├── available_farms
    │   └── default.farm
    ├── cache
    │   ├── default_invalidate.any
    │   ├── default_rules.any
    │   └── rules.any
    ├── clientheaders
    │   ├── clientheaders.any
    │   └── default_clientheaders.any
    ├── dispatcher.any
    ├── enabled_farms
    │   ├── README
    │   └── default.farm -> ../available_farms/default.farm
    ├── filters
    │   ├── default_filters.any
    │   └── filters.any
    ├── renders
    │   └── default_renders.any
    └── virtualhosts
        ├── default_virtualhosts.any
        └── virtualhosts.any
```

## Customizable files

The following files are customizable and will get transferred to your Cloud instance on deployment:

- `conf.d/available_vhosts/<CUSTOMER_CHOICE>.vhost`
  
    You can have one or more of these files, and they contain `<VirtualHost>` entries to
    match host names and allow Apache to handle each domain traffic with different rules. 
    Files are created in the `available_vhosts` directory and enabled with a symbolic link
    in the `enabled_vhosts` directory. From the `.vhost` files, other files like rewrites
    and variables will be included.

- `conf.d/rewrites/rewrite.rules`
  
    This file is included from inside your `.vhost` files. It has a set of rewrite rules
    for `mod_rewrite`.

- `conf.d/variables/custom.vars`
  
    This file is included from inside your `.vhost` files. You can put defines for Apache
    variables in there.

- `conf.d/variables/global.vars`
  
    This file is included from inside the `dispatcher_vhost.conf` file. You can change
    your dispatcher and rewrite log level in there.

- `conf.dispatcher.d/available_farms/<CUSTOMER_CHOICE>.farm`
  
    You can have one or more of these files, and they contain farms to match host names
    and allow the dispatcher module to handle each farm with different rules.
    Files are created in the `available_farms` directory and enabled with a symbolic link
    in the `enabled_farms` directory. From the `.farm` files, other files like filters,
    cache rules and others will be included.

- `conf.dispatcher.d/cache/rules.any`

    This file is included from inside your `.farm` files. It specifies caching preferences.

- `conf.dispatcher.d/clientheaders/clientheaders.any`

    This file is included from inside your `.farm` files. It specifies what request headers
    should be forwarded to the backend.

- `conf.dispatcher.d/filters/filters.any`
  
    This file is included from inside your `.farm` files. It has a set of rules that
    change what traffic should be filtered out and not make it to the backend.

- `conf.dispatcher.d/virtualhosts/virtualhosts.any`
  
    This file is included from inside your `.farm` files. It has a list of host names or
    URI paths to be matched by glob matching to determine what backend to use to serve
    a request.

## Environment Variables

- `DOCROOT`

    The Apache base document root. If you have one virtual host and farm, 
    use that variable as document root. In case of a multisite setup with
    sites located in a subtree, use an appropriate subfolder of that
    document root.

- `ENVIRONMENT_TYPE`

    The environment type of your image, either `dev`, `stage` or `prod`.

## Immutable Configuration Files

These files are part of the base framework and enforce standards and best practices.
They should be considered immutable, because modifying or deleting them locally will have
no impact on your deployment, as they will not get transferred to your Cloud instance.

- `conf.d/available_vhosts/default.vhost`
  
    Contains a sample virtual host. For your own virtual host, create a copy of this file, 
    customize it, go to `conf.d/enabled_vhosts` and create a symbolic link to your customized copy.

- `conf.d/dispatcher_vhost.conf`
  
    Part of base framework, used to illustrate how your virtual hosts and global variables are included. 

- `conf.d/rewrites/default_rewrite.rules`

    Default rewrite rules suitable for a standard project. If you need customization, modify 
    `rewrite.rules`. In your customization, you can still include the default rules first,
    if they suit your needs.

- `conf.dispatcher.d/available_farms/default.farm`

    Contains a sample dispatcher farm. For your own farm, create a copy of this file, customize
    it, go to `conf.d/enabled_farms` and create a symbolic link to your customized copy.

- `conf.dispatcher.d/cache/default_invalidate.any`

    Part of base framework, gets generated on startup. You are **required** to include this
    file in every farm you define, in the `cache/allowedClients` section.

- `conf.dispatcher.d/cache/default_rules.any`
    
    Default cache rules suitable for a standard project. If you need customization, modify 
    `conf.dispatcher.d/cache/rules.any`. In your customization, you can still include the
    default rules first, if they suit your needs.

- `conf.dispatcher.d/clientheaders/default_clientheaders.any`
    
    Default request headers to forward to backend, suitable for a standard project. If you
    need customization, modify `clientheaders.any`. In your customization, you can still
    include the default request headers first, if they suit your needs.

- `conf.dispatcher.d/dispatcher.any`
    
    Part of base framework, used to illustrate how your dispatcher farms are included. 

- `conf.dispatcher.d/filters/default_filters.any`
    
    Default filters suitable for a standard project. If you need customization, modify 
    `filters.any`. In your customization, you can still include the default filters
    first, if they suit your needs.

- `conf.dispatcher.d/renders/default_renders.any`
    
    Part of base framework, gets generated on startup. You are **required** to include this
    file in every farm you define, in the `renders` section.

- `conf.dispatcher.d/virtualhosts/default_virtualhosts.any`
    
    Default host globbing suitable for a standard project. If you need customization, modify 
    `virtualhosts.any`. In your customization, you *shouldn't* include the default host
    globbing, as it matches **every** incoming request.
