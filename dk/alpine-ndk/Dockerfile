FROM litf/alpine-glibc

ARG ANDROID_NDK_TOOLS=r19b
ENV ANDROID_NDK_HOME=${PWD}/android-ndk-${ANDROID_NDK_TOOLS}
ENV PATH=${PATH}:${ANDROID_NDK}

RUN apk --no-cache add bash wget gnupg openjdk8 unzip git curl python3 bzip2 make \
 && pip3 install --upgrade pip setuptools \
 && update-ca-certificates \
 && pip install -U setuptools \
 && pip install -U wheel \
 && pip install -U crcmod \
# NDK
 && wget -q -O android-ndk.zip https://dl.google.com/android/repository/android-ndk-${ANDROID_NDK_TOOLS}-linux-x86_64.zip \
 && unzip -qo android-ndk.zip \
 && rm android-ndk.zip \
