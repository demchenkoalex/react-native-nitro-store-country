#include <jni.h>
#include "NitroStoreCountryOnLoad.hpp"

JNIEXPORT jint JNICALL JNI_OnLoad(JavaVM* vm, void*) {
  return margelo::nitro::nitrostorecountry::initialize(vm);
}
