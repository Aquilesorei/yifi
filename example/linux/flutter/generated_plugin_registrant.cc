//
//  Generated file. Do not edit.
//

// clang-format off

#include "generated_plugin_registrant.h"

#include <yifi/yifi_plugin.h>

void fl_register_plugins(FlPluginRegistry* registry) {
  g_autoptr(FlPluginRegistrar) yifi_registrar =
      fl_plugin_registry_get_registrar_for_plugin(registry, "YifiPlugin");
  yifi_plugin_register_with_registrar(yifi_registrar);
}
