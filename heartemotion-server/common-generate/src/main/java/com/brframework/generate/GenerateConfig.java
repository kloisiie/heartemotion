package com.brframework.generate;

/**
 * @author xu
 * @date 2019/12/24 14:57
 */
public class GenerateConfig {

    static String temp = "\n" +
            "    @Autowired\n" +
            "    ConfigService<{upperName}Config> {name}ConfigService;\n" +
            "    @ApiOperation(value = \"{title}\", notes = \"{title}\")\n" +
            "    @PostMapping(\"/v1/system-config/{name}/config\")\n" +
            "    @EditLayout(paddingMappingMethod = \"get{upperName}\")\n" +
            "    @AdminMenu(menuName = \"{title}\")\n" +
            "    @PreAuthorize(\"hasRole('system-config-{name}-config')\")\n" +
            "    @AOLog\n" +
            "    public JSONResult post{upperName}(@RequestBody {upperName}Config param) {\n" +
            "        {name}ConfigService.set(param);\n" +
            "        return JSONResult.ok();\n" +
            "    }\n" +
            "\n" +
            "    @ApiOperation(value = \"获取{title}\", notes = \"获取{title}\")\n" +
            "    @PreAuthorize(\"hasRole('system-config-{name}-config')\")\n" +
            "    @GetMapping(\"/v1/system-config/{name}/config\")\n" +
            "    public JSONResult<{upperName}Config> get{upperName}() {\n" +
            "        return JSONResult.ok({name}ConfigService.get());\n" +
            "    }\n";

    public static void main(String[] args) {
        String title = "平台配置";
        String name = "platform";
        String upperName = name.substring(0, 1).toUpperCase() + name.substring(1);
        String value = temp.replace("{title}", title).replace("{name}", name)
                .replace("{upperName}", upperName);
        System.out.println(value);
    }

}
